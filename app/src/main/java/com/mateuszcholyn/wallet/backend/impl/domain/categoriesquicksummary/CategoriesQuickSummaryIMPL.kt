package com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.MainCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryListV2
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.SubCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.MainCategory
import com.mateuszcholyn.wallet.backend.api.core.category.findOrThrow
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.CategoryPair
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent


class CategoriesQuickSummaryIMPL(
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : CategoriesQuickSummaryAPI {
    override suspend fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent) {
        categoryAddedEvent
            .toQuickSummaryResult()
            .also { categoriesQuickSummaryRepository.saveQuickSummaryResult(it) }
    }

    override suspend fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent) {
        categoriesQuickSummaryRepository.remove(categoryRemovedEvent.categoryId)
    }

    override suspend fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        incWithOne(expenseAddedEvent.categoryId.resolveCategoryId())
    }

    override suspend fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        decWithOne(expenseUpdatedEvent.oldCategoryId.resolveCategoryId())
        incWithOne(expenseUpdatedEvent.newCategoryId.resolveCategoryId())
    }

    private fun CategoryPair.resolveCategoryId(): CategoryId =
        subCategoryId
            ?.let { CategoryId(it.id) }
            ?: categoryId

    override suspend fun getQuickSummary(): QuickSummaryList {
        val allCategories = categoryCoreServiceAPI.getAll()
        return categoriesQuickSummaryRepository
            .getQuickSummaries()
            .sortedByDescending { it.numberOfExpenses }
            .map { it.toCategoryQuickSummary(allCategories) }
            .let { QuickSummaryList(it) }
    }

    override suspend fun getQuickSummaryV2(): QuickSummaryListV2 =
        joinCategoriesWithQuickSummaryInfo(
            categories = categoryCoreServiceAPI.getAllGrouped().mainCategories,
            categoriesQuickSummaryResult = categoriesQuickSummaryRepository.getQuickSummaries(),
        )

    private fun joinCategoriesWithQuickSummaryInfo(
        categories: List<MainCategory>,
        categoriesQuickSummaryResult: List<CategoryQuickSummaryResult>,
    ): QuickSummaryListV2 {
        val mainCategoriesQuickSummaries =
            categories
                .map { mainCategory ->
                    val subCategoriesQuickSummaries =
                        mainCategory
                            .subCategories
                            .map { subCategory ->
                                SubCategoryQuickSummary(
                                    id = subCategory.id,
                                    name = subCategory.name,
                                    numberOfExpenses = categoriesQuickSummaryResult
                                        .first { it.categoryId == subCategory.id }.numberOfExpenses,
                                    mainCategoryId = mainCategory.id,
                                )
                            }
                            .sortedByDescending { it.numberOfExpenses }

                    MainCategoryQuickSummary(
                        id = mainCategory.id,
                        name = mainCategory.name,
                        numberOfExpenses = categoriesQuickSummaryResult
                            .first { it.categoryId == mainCategory.id }.numberOfExpenses +
                            subCategoriesQuickSummaries.sumOf { it.numberOfExpenses },
                        subCategories = subCategoriesQuickSummaries,
                    )
                }

        return QuickSummaryListV2(mainCategoriesQuickSummaries)
    }

    override suspend fun removeAll() {
        categoriesQuickSummaryRepository.removeAll()
    }

    private fun CategoryQuickSummaryResult.toCategoryQuickSummary(
        allCategories: List<Category>,
    ): CategoryQuickSummary =
        CategoryQuickSummary(
            categoryId = categoryId,
            categoryName = allCategories.findOrThrow(categoryId).name,
            numberOfExpenses = numberOfExpenses,
        )

    override suspend fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        decWithOne(expenseRemovedEvent.categoryId)
    }

    private fun CategoryAddedEvent.toQuickSummaryResult(): CategoryQuickSummaryResult =
        CategoryQuickSummaryResult(
            categoryId = categoryId,
            numberOfExpenses = 0,
        )

    private suspend fun incWithOne(categoryId: CategoryId) {
        categoriesQuickSummaryRepository
            .findByCategoryIdOrThrow(categoryId)
            .incWithOne()
            .also { categoriesQuickSummaryRepository.saveQuickSummaryResult(it) }
    }

    private suspend fun decWithOne(categoryId: CategoryId) {
        categoriesQuickSummaryRepository
            .findByCategoryIdOrThrow(categoryId)
            .decWithOne()
            .also { categoriesQuickSummaryRepository.saveQuickSummaryResult(it) }
    }

}

private suspend fun CategoriesQuickSummaryRepository.findByCategoryIdOrThrow(categoryId: CategoryId) =
    this.findByCategoryId(categoryId)
        ?: throw CategoryQuickSummaryNotFoundException(categoryId)


class CategoryQuickSummaryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category quick summary for category with id ${categoryId.id} does not exist")

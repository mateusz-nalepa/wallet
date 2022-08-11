package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.findOrThrow
import com.mateuszcholyn.wallet.newcode.app.backend.events.*


class CategoriesQuickSummaryIMPL(
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : CategoriesQuickSummaryAPI {
    override fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent) {
        categoryAddedEvent
            .toQuickSummaryResult()
            .also { categoriesQuickSummaryRepository.saveQuickSummaryResult(it) }
    }

    override fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent) {
        categoriesQuickSummaryRepository.remove(categoryRemovedEvent.categoryId)
    }

    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        incWithOne(expenseAddedEvent.categoryId)
    }

    override fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        decWithOne(expenseUpdatedEvent.oldCategoryId)
        incWithOne(expenseUpdatedEvent.newCategoryId)
    }

    override fun getQuickSummary(): QuickSummaryList {
        val allCategories = categoryCoreServiceAPI.getAll()
        return categoriesQuickSummaryRepository
            .getQuickSummaries()
            .map { it.toCategoryQuickSummary(allCategories) }
            .let { QuickSummaryList(it) }
    }

    private fun CategoryQuickSummaryResult.toCategoryQuickSummary(
        allCategories: List<CategoryV2>,
    ): CategoryQuickSummary =
        CategoryQuickSummary(
            categoryId = categoryId,
            categoryName = allCategories.findOrThrow(categoryId).name,
            numberOfExpenses = numberOfExpenses,
        )

    override fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        decWithOne(expenseRemovedEvent.categoryId)
    }

    private fun CategoryAddedEvent.toQuickSummaryResult(): CategoryQuickSummaryResult =
        CategoryQuickSummaryResult(
            categoryId = categoryId,
            numberOfExpenses = 0,
        )

    private fun incWithOne(categoryId: CategoryId) {
        categoriesQuickSummaryRepository
            .findByCategoryIdOrThrow(categoryId)
            .incWithOne()
            .also { categoriesQuickSummaryRepository.saveQuickSummaryResult(it) }
    }

    private fun decWithOne(categoryId: CategoryId) {
        categoriesQuickSummaryRepository
            .findByCategoryIdOrThrow(categoryId)
            .decWithOne()
            .also { categoriesQuickSummaryRepository.saveQuickSummaryResult(it) }
    }

}

private fun CategoriesQuickSummaryRepository.findByCategoryIdOrThrow(categoryId: CategoryId) =
    this.findByCategoryId(categoryId)
        ?: throw CategoryQuickSummaryNotFoundException(categoryId)


class CategoryQuickSummaryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category quick summary for category with id ${categoryId.id} does not exist")

package com.mateuszcholyn.wallet.backend.impl.domain.searchservice

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.MainCategory
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategory
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent

class SearchServiceIMPL(
    private val searchServiceRepository: SearchServiceRepository,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : SearchServiceAPI {
    override suspend fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        searchServiceRepository.remove(expenseRemovedEvent.expenseId)
    }

    override suspend fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        searchServiceRepository
            .getById(expenseId = expenseUpdatedEvent.expenseId)
            ?.updateUsing(expenseUpdatedEvent)
            ?.also { searchServiceRepository.saveExpense(it) }
    }

    override suspend fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        expenseAddedEvent
            .toSearchSingleResult()
            .let { searchServiceRepository.saveExpense(it) }
    }

    override suspend fun getAll(
        searchCriteria: SearchCriteria,
    ): SearchServiceResult {
        val allCategories = categoryCoreServiceAPI.getAllGrouped()

        return searchServiceRepository
            .getAll(searchCriteria)
            .toSearchServiceResult(allCategories.mainCategories, searchCriteria)
    }

    override suspend fun removeAll() {
        searchServiceRepository.removeAll()
    }

    private fun List<SearchSingleResultRepo>.toSearchServiceResult(
        allCategories: List<MainCategory>,
        searchCriteria: SearchCriteria,
    ): SearchServiceResult {

        val expensesWithDetails = this.map { it.toSearchSingleResult(allCategories) }

        return SearchServiceResult(
            expenses = expensesWithDetails,
            averageExpenseResult = SearchAverageExpenseResultCalculator.calculate(
                expenses = expensesWithDetails,
                searchCriteria = searchCriteria,
            )
        )
    }

    private fun SearchSingleResultRepo.updateUsing(
        expenseUpdatedEvent: ExpenseUpdatedEvent,
    ): SearchSingleResultRepo =
        this.copy(
            amount = expenseUpdatedEvent.newAmount,
            paidAt = expenseUpdatedEvent.newPaidAt,
            categoryId = expenseUpdatedEvent.newCategoryId.categoryId,
            subCategoryId = expenseUpdatedEvent.newCategoryId.subCategoryId,
            description = expenseUpdatedEvent.newDescription,
        )

    private fun ExpenseAddedEvent.toSearchSingleResult(): SearchSingleResultRepo =
        SearchSingleResultRepo(
            expenseId = expenseId,
            categoryId = categoryId.categoryId,
            subCategoryId = categoryId.subCategoryId,
            amount = amount,
            paidAt = paidAt,
            description = description,
        )
}

private fun SearchSingleResultRepo.toSearchSingleResult(
    allCategories: List<MainCategory>,
): SearchSingleResult {

    val mainCategory = allCategories.findOrThrow(categoryId)

    return SearchSingleResult(
        expenseId = expenseId,

        categoryId = categoryId,
        categoryName = mainCategory.name,

        subCategoryId = subCategoryId?.id?.let { SubCategoryId(it) },
        subCategoryName = subCategoryId?.let { mainCategory.findSubCategoryOrThrow(it).name },

        amount = amount,
        paidAt = paidAt,
        description = description,
    )
}

fun List<MainCategory>.findOrThrow(categoryId: CategoryId): MainCategory =
    this
        .find { it.id == categoryId }
        ?: throw IllegalStateException("Category is missing. Should not happen :D")

fun MainCategory.findSubCategoryOrThrow(subCategoryId: SubCategoryId): SubCategory =
    this
        .subCategories
        .find { it.id.id == subCategoryId.id }
        ?: throw IllegalStateException("Subcategory is missing. Should not happen :D")
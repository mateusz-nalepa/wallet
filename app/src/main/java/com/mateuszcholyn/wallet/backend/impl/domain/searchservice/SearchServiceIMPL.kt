package com.mateuszcholyn.wallet.backend.impl.domain.searchservice

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.findOrThrow
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
        searchServiceRepository.saveExpense(expenseAddedEvent.toSearchSingleResult())
    }

    override suspend fun getAll(
        searchCriteria: SearchCriteria,
    ): SearchServiceResult {
        val allCategories = categoryCoreServiceAPI.getAll()

        return searchServiceRepository
            .getAll(searchCriteria)
            .toSearchServiceResult(allCategories, searchCriteria)
    }

    override suspend fun removeAll() {
        searchServiceRepository.removeAll()
    }

    private fun List<SearchSingleResultRepo>.toSearchServiceResult(
        allCategories: List<Category>,
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
            categoryId = expenseUpdatedEvent.newCategoryId,
            description = expenseUpdatedEvent.newDescription,
        )

    private fun ExpenseAddedEvent.toSearchSingleResult(): SearchSingleResultRepo =
        SearchSingleResultRepo(
            expenseId = expenseId,
            categoryId = categoryId,
            amount = amount,
            paidAt = paidAt,
            description = description,
        )
}

private fun SearchSingleResultRepo.toSearchSingleResult(
    allCategories: List<Category>,
): SearchSingleResult =
    SearchSingleResult(
        expenseId = expenseId,
        categoryId = categoryId,
        categoryName = allCategories.findOrThrow(categoryId).name,
        amount = amount,
        paidAt = paidAt,
        description = description,
    )

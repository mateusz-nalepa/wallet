package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.findOrThrow
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseUpdatedEvent

class SearchServiceIMPL(
    private val searchServiceRepository: SearchServiceRepository,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : SearchServiceAPI {
    override fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        searchServiceRepository.remove(expenseRemovedEvent.expenseId)
    }

    override fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        searchServiceRepository
            .getById(expenseId = expenseUpdatedEvent.expenseId)
            ?.updateUsing(expenseUpdatedEvent)
            ?.also { searchServiceRepository.saveExpense(it) }
    }

    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        searchServiceRepository.saveExpense(expenseAddedEvent.toSearchSingleResult())
    }

    override fun getAll(
        searchCriteria: SearchCriteria,
    ): SearchServiceResult {
        // HODOR - write test for that
        val allCategories = categoryCoreServiceAPI.getAll()

        return searchServiceRepository
            .getAll(searchCriteria)
            .toSearchServiceResult(allCategories, searchCriteria)
    }

    private fun List<SearchSingleResultRepo>.toSearchServiceResult(
        allCategories: List<CategoryV2>,
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
    allCategories: List<CategoryV2>,
): SearchSingleResult =
    SearchSingleResult(
        expenseId = expenseId,
        categoryId = categoryId,
        categoryName = allCategories.findOrThrow(categoryId).name,
        amount = amount,
        paidAt = paidAt,
        description = description,
    )

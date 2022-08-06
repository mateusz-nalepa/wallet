package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent

class SearchServiceIMPL(
    private val searchServiceRepository: SearchServiceRepository,
) : SearchServiceAPI {
    override fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        searchServiceRepository.remove(expenseRemovedEvent.expenseId)
    }

    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        searchServiceRepository.saveExpense(expenseAddedEvent)
    }

    override fun getAll(
        searchCriteria: SearchCriteria,
    ): SearchServiceResult =
        searchServiceRepository
            .getAll(searchCriteria)
            .toSearchServiceResult(searchCriteria)

    private fun List<ExpenseAddedEvent>.toSearchServiceResult(
        searchCriteria: SearchCriteria,
    ): SearchServiceResult =
        SearchServiceResult(
            expenses = this,
            averageExpenseResult = SearchAverageExpenseResultCalculator.calculate(
                expenses = this,
                searchCriteria = searchCriteria,
            )
        )
}

package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.core.ExpenseId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent


interface SearchServiceRepository {
    fun saveExpense(expenseAddedEvent: ExpenseAddedEvent): ExpenseAddedEvent
    fun getAll(searchCriteria: SearchCriteria): List<ExpenseAddedEvent>
}

class InMemorySearchServiceRepository : SearchServiceRepository {
    private val storage: MutableMap<ExpenseId, ExpenseAddedEvent> = mutableMapOf()

    override fun saveExpense(expenseAddedEvent: ExpenseAddedEvent): ExpenseAddedEvent {
        storage[expenseAddedEvent.expenseId] = expenseAddedEvent
        return expenseAddedEvent
    }

    override fun getAll(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        storage.values.toList()

}

class SearchServiceIMPL(
    private val searchServiceRepository: SearchServiceRepository,
) : SearchServiceAPI {

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

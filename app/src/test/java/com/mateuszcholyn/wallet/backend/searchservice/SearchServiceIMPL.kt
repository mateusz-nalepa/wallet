package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseId


interface SearchServiceRepository {
    fun saveExpense(expenseAddedEvent: ExpenseAddedEvent): ExpenseAddedEvent
    fun getAll(): List<ExpenseAddedEvent>
}

class InMemorySearchServiceRepository : SearchServiceRepository {
    private val storage: MutableMap<ExpenseId, ExpenseAddedEvent> = mutableMapOf()

    override fun saveExpense(expenseAddedEvent: ExpenseAddedEvent): ExpenseAddedEvent {
        storage[expenseAddedEvent.expenseId] = expenseAddedEvent
        return expenseAddedEvent
    }

    override fun getAll(): List<ExpenseAddedEvent> =
        storage.values.toList()

}

class SearchServiceIMPL(
    private val searchServiceRepository: SearchServiceRepository,
) : SearchServiceAPI {

    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        searchServiceRepository.saveExpense(expenseAddedEvent)
    }

    override fun getAll(): ExpensesList =
        searchServiceRepository
            .getAll()
            .let { ExpensesList(it) }
}

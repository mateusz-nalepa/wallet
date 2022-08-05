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
        storage
            .values
            .toList()
            .filter {
                it.paidAt.isAfter(searchCriteria.beginDate) || it.paidAt.isEqual(searchCriteria.beginDate)
            }
            .filter {
                it.paidAt.isBefore(searchCriteria.endDate) || it.paidAt.isEqual(searchCriteria.endDate)
            }
            .filterByCategory(searchCriteria)

    private fun List<ExpenseAddedEvent>.filterByCategory(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        if (searchCriteria.allCategories) {
            this
        } else {
            filter { it.categoryId == searchCriteria.categoryId }
        }
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

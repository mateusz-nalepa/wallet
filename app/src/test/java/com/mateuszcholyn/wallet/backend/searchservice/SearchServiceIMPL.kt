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
            .filterByBeginDate(searchCriteria)
            .filterByEndDate(searchCriteria)
            .filterByCategory(searchCriteria)

    private fun List<ExpenseAddedEvent>.filterByBeginDate(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        if (searchCriteria.beginDate == null) {
            this
        } else {
            filter {
                it.paidAt.isAfter(searchCriteria.beginDate) || it.paidAt.isEqual(searchCriteria.beginDate)
            }
        }

    private fun List<ExpenseAddedEvent>.filterByEndDate(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        if (searchCriteria.endDate == null) {
            this
        } else {
            filter {
                it.paidAt.isBefore(searchCriteria.endDate) || it.paidAt.isEqual(searchCriteria.endDate)
            }
        }

    private fun List<ExpenseAddedEvent>.filterByCategory(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        if (searchCriteria.categoryId != null && (searchCriteria.allCategories == null || searchCriteria.allCategories == false)) {
            filter { it.categoryId == searchCriteria.categoryId }
        } else if (searchCriteria.allCategories == null && searchCriteria.allCategories != false) {
            this
        } else {
            this
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

package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.core.ExpenseId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent

interface SearchServiceRepository {
    fun saveExpense(expenseAddedEvent: ExpenseAddedEvent): ExpenseAddedEvent
    fun getAll(searchCriteria: SearchCriteria): List<ExpenseAddedEvent>
    fun remove(expenseId: ExpenseId)
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
            .filterByFromAmount(searchCriteria)
            .filterByToAmount(searchCriteria)
            .sort(searchCriteria)

    override fun remove(expenseId: ExpenseId) {
        storage.remove(expenseId)
    }

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
        if (searchCriteria.categoryId != null) {
            filter { it.categoryId == searchCriteria.categoryId }
        } else {
            this
        }

    private fun List<ExpenseAddedEvent>.filterByFromAmount(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        if (searchCriteria.fromAmount != null) {
            filter { it.amount >= searchCriteria.fromAmount }
        } else {
            this
        }

    private fun List<ExpenseAddedEvent>.filterByToAmount(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> =
        if (searchCriteria.toAmount != null) {
            filter { it.amount <= searchCriteria.toAmount }
        } else {
            this
        }

    private fun List<ExpenseAddedEvent>.sort(searchCriteria: SearchCriteria): List<ExpenseAddedEvent> {
        val sortedByField =
            when (searchCriteria.sort.field) {
                NewSort.Field.DATE -> sortedBy { it.paidAt }
                NewSort.Field.AMOUNT -> sortedBy { it.amount }
            }

        return when (searchCriteria.sort.type) {
            NewSort.Type.DESC -> sortedByField.reversed()
            NewSort.Type.ASC -> sortedByField
        }
    }
}
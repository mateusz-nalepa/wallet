package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseUpdatedEvent

class SearchServiceIMPL(
    private val searchServiceRepository: SearchServiceRepository,
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

    private fun ExpenseAddedEvent.updateUsing(
        expenseUpdatedEvent: ExpenseUpdatedEvent,
    ): ExpenseAddedEvent =
        this.copy(
            amount = expenseUpdatedEvent.newAmount,
            paidAt = expenseUpdatedEvent.newPaidAt,
            categoryId = expenseUpdatedEvent.newCategoryId,
        )
}

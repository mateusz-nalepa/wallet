package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

interface ExpensePublisher {
    fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent)
    fun publishExpenseUpdatedEvent(expenseUpdatedEvent: ExpenseUpdatedEvent)
    fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent)
}

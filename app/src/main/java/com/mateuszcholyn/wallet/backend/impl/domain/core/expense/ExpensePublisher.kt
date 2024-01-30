package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

interface ExpensePublisher {
    suspend fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent)
    suspend fun publishExpenseUpdatedEvent(expenseUpdatedEvent: ExpenseUpdatedEvent)
    suspend fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent)
}

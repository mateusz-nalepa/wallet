package com.mateuszcholyn.wallet.backend.impl.infrastructure.messagebus.core.expense

import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpensePublisher
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.messagebus.MessageBus

class MessageBusExpensePublisher(
    private val messageBus: MessageBus,
) : ExpensePublisher {
    override suspend fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent) {
        messageBus.expenseAddedEventTopic.publish(expenseAddedEvent)
    }

    override suspend fun publishExpenseUpdatedEvent(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        messageBus.expenseUpdatedEventTopic.publish(expenseUpdatedEvent)
    }

    override suspend fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent) {
        messageBus.expenseRemovedEventTopic.publish(expenseRemovedEvent)
    }
}
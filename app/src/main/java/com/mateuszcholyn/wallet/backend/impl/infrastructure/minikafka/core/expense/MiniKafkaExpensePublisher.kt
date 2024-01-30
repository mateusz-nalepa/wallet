package com.mateuszcholyn.wallet.backend.impl.infrastructure.minikafka.core.expense

import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpensePublisher
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.minikafka.MiniKafka

class MiniKafkaExpensePublisher(
    private val miniKafka: MiniKafka,
) : ExpensePublisher {
    override suspend fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent) {
        miniKafka.expenseAddedEventTopic.publish(expenseAddedEvent)
    }

    override suspend fun publishExpenseUpdatedEvent(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        miniKafka.expenseUpdatedEventTopic.publish(expenseUpdatedEvent)
    }

    override suspend fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent) {
        miniKafka.expenseRemovedEventTopic.publish(expenseRemovedEvent)
    }
}
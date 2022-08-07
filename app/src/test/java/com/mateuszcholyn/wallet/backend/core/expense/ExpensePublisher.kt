package com.mateuszcholyn.wallet.backend.core.expense

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.events.MiniKafka

interface ExpensePublisher {
    fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent)
    fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent)
}

class MiniKafkaExpensePublisher(
    private val miniKafka: MiniKafka,
) : ExpensePublisher {
    override fun publishExpenseAddedEvent(expenseAddedEvent: ExpenseAddedEvent) {
        miniKafka.expenseAddedEventTopic.publish(expenseAddedEvent)
    }

    override fun publishExpenseRemovedEvent(expenseRemovedEvent: ExpenseRemovedEvent) {
        miniKafka.expenseRemovedEventTopic.publish(expenseRemovedEvent)
    }
}
package com.mateuszcholyn.wallet.backend.impl.domain.messagebus

import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent

class MessageBus {
    val expenseAddedEventTopic: Topic<ExpenseAddedEvent> = TopicImpl()
    val expenseUpdatedEventTopic: Topic<ExpenseUpdatedEvent> = TopicImpl()
    val expenseRemovedEventTopic: Topic<ExpenseRemovedEvent> = TopicImpl()
    val categoryAddedEventTopic: Topic<CategoryAddedEvent> = TopicImpl()
    val categoryRemovedEventTopic: Topic<CategoryRemovedEvent> = TopicImpl()
}


fun interface Subscription<T> {
    suspend fun onMessagePublished(t: T)
}

interface Topic<T> {
    suspend fun publish(t: T)
    fun addSubscription(subscription: Subscription<T>)
}

class TopicImpl<T> : Topic<T> {
    private val subscriptions: MutableList<Subscription<T>> = mutableListOf()

    override fun addSubscription(subscription: Subscription<T>) {
        subscriptions.add(subscription)
    }

    override suspend fun publish(t: T) {
        subscriptions.forEach { it.onMessagePublished(t) }
    }

}

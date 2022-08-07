package com.mateuszcholyn.wallet.newcode.app.backend.events

class MiniKafka {
    val expenseAddedEventTopic: Topic<ExpenseAddedEvent> = TopicImpl()
    val expenseUpdatedEventTopic: Topic<ExpenseUpdatedEvent> = TopicImpl()
    val expenseRemovedEventTopic: Topic<ExpenseRemovedEvent> = TopicImpl()
    val categoryAddedEventTopic: Topic<CategoryAddedEvent> = TopicImpl()
    val categoryRemovedEventTopic: Topic<CategoryRemovedEvent> = TopicImpl()
}

interface Topic<T> {
    fun publish(t: T)
    fun addSubscription(subscription: Subscription<T>)
}

fun interface Subscription<T> {
    fun onMessagePublished(t: T)
}

class TopicImpl<T> : Topic<T> {
    private val subscriptions: MutableList<Subscription<T>> = mutableListOf()

    override fun addSubscription(subscription: Subscription<T>) {
        subscriptions.add(subscription)
    }

    override fun publish(t: T) {
        subscriptions.forEach { it.onMessagePublished(t) }
    }

}

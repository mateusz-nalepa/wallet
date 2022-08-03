package com.mateuszcholyn.wallet.backend.events

class MiniKafka {
    val expenseAddedEventTopic: Topic<ExpenseAddedEvent> = TopicImpl()
    val categoryAddedEventTopic: Topic<CategoryAddedEvent> = TopicImpl()
}

interface Topic<T> {
    fun publish(t: T)
    fun addSubscriber(subscriber: Subscriber<T>)
}

fun interface Subscriber<T> {
    fun onMessagePublished(t: T)
}

class TopicImpl<T> : Topic<T> {
    private val subscribers: MutableList<Subscriber<T>> = mutableListOf()

    override fun addSubscriber(subscriber: Subscriber<T>) {
        subscribers.add(subscriber)
    }

    override fun publish(t: T) {
        subscribers
            .forEach { it.onMessagePublished(t) }
    }

}



package com.mateuszcholyn.wallet.backend.events

class MiniKafka {
    val expenseAddedEventTopic: Topic<ExpenseAddedEvent> = ExpenseAddedEventTopic()
}

interface Topic<T> {
    fun publish(t: T)
    fun addSubscriber(subscriber: Subscriber<T>)
}

fun interface Subscriber<T> {
    fun onMessagePublished(t: T)
}

class ExpenseAddedEventTopic : Topic<ExpenseAddedEvent> {
    private val subscribers: MutableList<Subscriber<ExpenseAddedEvent>> = mutableListOf()

    override fun addSubscriber(subscriber: Subscriber<ExpenseAddedEvent>) {
        subscribers.add(subscriber)
    }

    override fun publish(t: ExpenseAddedEvent) {
        subscribers
            .forEach { it.onMessagePublished(t) }
    }

}



package com.mateuszcholyn.wallet.backend.core

import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.MiniKafka

interface CategoryPublisher {
    fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent)
}

class MiniKafkaCategoryPublisher(
    private val miniKafka: MiniKafka,
) : CategoryPublisher {
    override fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent) {
        miniKafka.categoryAddedEventTopic.publish(categoryAddedEvent)
    }
}
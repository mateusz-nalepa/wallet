package com.mateuszcholyn.wallet.backend.core

import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.events.MiniKafka

interface CategoryPublisher {
    fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent)
    fun publishCategoryRemovedEvent(categoryRemovedEvent: CategoryRemovedEvent)
}

class MiniKafkaCategoryPublisher(
    private val miniKafka: MiniKafka,
) : CategoryPublisher {
    override fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent) {
        miniKafka.categoryAddedEventTopic.publish(categoryAddedEvent)
    }

    override fun publishCategoryRemovedEvent(categoryRemovedEvent: CategoryRemovedEvent) {
        miniKafka.categoryRemovedEventTopic.publish(categoryRemovedEvent)
    }
}
package com.mateuszcholyn.wallet.app.backend.core.category

import com.mateuszcholyn.wallet.app.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.app.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.app.backend.events.MiniKafka

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
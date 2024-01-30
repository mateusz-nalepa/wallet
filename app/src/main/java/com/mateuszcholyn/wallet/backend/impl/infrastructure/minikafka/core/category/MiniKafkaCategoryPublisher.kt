package com.mateuszcholyn.wallet.backend.impl.infrastructure.minikafka.core.category

import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryPublisher
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.minikafka.MiniKafka

class MiniKafkaCategoryPublisher(
    private val miniKafka: MiniKafka,
) : CategoryPublisher {
    override suspend fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent) {
        miniKafka.categoryAddedEventTopic.publish(categoryAddedEvent)
    }

    override suspend fun publishCategoryRemovedEvent(categoryRemovedEvent: CategoryRemovedEvent) {
        miniKafka.categoryRemovedEventTopic.publish(categoryRemovedEvent)
    }
}
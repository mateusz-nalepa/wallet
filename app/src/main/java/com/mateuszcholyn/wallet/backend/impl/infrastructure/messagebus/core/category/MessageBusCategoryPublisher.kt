package com.mateuszcholyn.wallet.backend.impl.infrastructure.messagebus.core.category

import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryPublisher
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.messagebus.MessageBus

class MessageBusCategoryPublisher(
    private val messageBus: MessageBus,
) : CategoryPublisher {
    override suspend fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent) {
        messageBus.categoryAddedEventTopic.publish(categoryAddedEvent)
    }

    override suspend fun publishCategoryRemovedEvent(categoryRemovedEvent: CategoryRemovedEvent) {
        messageBus.categoryRemovedEventTopic.publish(categoryRemovedEvent)
    }
}
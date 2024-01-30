package com.mateuszcholyn.wallet.backend.impl.domain.core.category

interface CategoryPublisher {
    suspend fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent)
    suspend fun publishCategoryRemovedEvent(categoryRemovedEvent: CategoryRemovedEvent)
}


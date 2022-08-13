package com.mateuszcholyn.wallet.backend.impl.domain.core.category

interface CategoryPublisher {
    fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent)
    fun publishCategoryRemovedEvent(categoryRemovedEvent: CategoryRemovedEvent)
}


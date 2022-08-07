package com.mateuszcholyn.wallet.app.backend.events

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId

data class CategoryAddedEvent(
    val categoryId: CategoryId,
    val name: String,
)

data class CategoryUpdatedEvent(
    val categoryId: CategoryId,
    val newName: String,
)

data class CategoryRemovedEvent(
    val categoryId: CategoryId,
)

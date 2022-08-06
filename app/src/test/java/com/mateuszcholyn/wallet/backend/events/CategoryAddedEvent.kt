package com.mateuszcholyn.wallet.backend.events

import com.mateuszcholyn.wallet.backend.core.CategoryId

data class CategoryAddedEvent(
    val categoryId: CategoryId,
    val name: String,
)

data class CategoryRemovedEvent(
    val categoryId: CategoryId,
)

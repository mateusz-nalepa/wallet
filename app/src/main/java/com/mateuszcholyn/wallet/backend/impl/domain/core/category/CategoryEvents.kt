package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId

data class CategoryAddedEvent(
    val categoryId: CategoryId,
    val name: String,
)

data class CategoryRemovedEvent(
    val categoryId: CategoryId,
)

package com.mateuszcholyn.wallet.domain.category.model

data class CategoryDto(
        var id: Long = -1,
        var active: Boolean = true,
        var name: String
)

package com.mateuszcholyn.wallet.domain.category.model

import java.io.Serializable

data class CategoryDto(
        var id: Long = -1,
        var name: String
) : Serializable

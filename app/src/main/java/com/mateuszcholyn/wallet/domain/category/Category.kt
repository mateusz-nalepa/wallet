package com.mateuszcholyn.wallet.domain.category

import java.io.Serializable

data class Category(
        var id: Long = -1,
        var name: String
) : Serializable

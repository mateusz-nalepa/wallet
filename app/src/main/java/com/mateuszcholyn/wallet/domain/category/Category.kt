package com.mateuszcholyn.wallet.domain.category

import java.io.Serializable

data class Category(
    var id: Long? = null,
    var name: String
) : Serializable


data class ExistingCategory(
    var id: String,
    var name: String
) : Serializable
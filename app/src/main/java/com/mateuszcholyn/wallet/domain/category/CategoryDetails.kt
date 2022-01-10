package com.mateuszcholyn.wallet.domain.category

import java.io.Serializable

data class CategoryDetails(
        val id: Long,
        val name: String,
        val numberOfExpenses: Long,
) : Serializable

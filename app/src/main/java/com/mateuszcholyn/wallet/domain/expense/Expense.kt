package com.mateuszcholyn.wallet.domain.expense

import com.mateuszcholyn.wallet.domain.category.Category
import java.io.Serializable
import java.math.BigDecimal
import java.time.LocalDateTime

data class Expense(
        var id: Long? = null,

        val amount: BigDecimal,
        val date: LocalDateTime,
        val description: String,

        val category: Category
) : Serializable {

    fun idOrThrow(): Long =
            id ?: throw IllegalStateException()
}
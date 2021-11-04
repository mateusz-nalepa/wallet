package com.mateuszcholyn.wallet.domain.expense

import com.mateuszcholyn.wallet.domain.category.Category
import org.joda.time.LocalDateTime
import java.io.Serializable

data class Expense(
    var id: Long = -1,

    val amount: Double,
    val date: LocalDateTime,
    val description: String,

    val category: Category
) : Serializable
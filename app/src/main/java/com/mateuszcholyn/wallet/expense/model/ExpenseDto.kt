package com.mateuszcholyn.wallet.expense.model

import java.io.Serializable
import java.util.*

data class ExpenseDto(
        var id: Long = -1,
        val active: Boolean = true,

        val amount: Double,
        val category: String,
        val date: Calendar,
        val description: String
) : Serializable
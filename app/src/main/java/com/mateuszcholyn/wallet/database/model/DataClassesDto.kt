package com.mateuszcholyn.wallet.database.model

import java.util.*

data class ExpenseDto(
        val amount: Double,
        val category: String,
        val date: Calendar
)
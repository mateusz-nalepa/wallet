package com.mateuszcholyn.wallet.expense.model

import java.io.Serializable
import java.util.*

data class ExpenseSearchCriteria(
        val categoryName: String,
        val beginDate: Calendar,
        val endDate: Calendar
) : Serializable
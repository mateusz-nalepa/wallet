package com.mateuszcholyn.wallet.expense.model

import java.util.*

data class AverageSearchCriteria(
        val categoryName: String,
        val beginDate: Calendar,
        val endDate: Calendar
)


package com.mateuszcholyn.wallet.expense.model

import java.util.*

data class ExpenseSearchCriteria(
        val categoryName : String? = null,
        val beginDate : Calendar? = null,
        val endDate : Calendar? = null
)

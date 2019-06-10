package com.mateuszcholyn.wallet.expense.model

import com.mateuszcholyn.wallet.expense.activity.ALL_CATEGORIES
import java.io.Serializable
import java.util.*

data class ExpenseSearchCriteria(
        val categoryName: String,
        val beginDate: Calendar,
        val endDate: Calendar
) : Serializable {
    fun isAllCategories() =
            ALL_CATEGORIES == categoryName
}

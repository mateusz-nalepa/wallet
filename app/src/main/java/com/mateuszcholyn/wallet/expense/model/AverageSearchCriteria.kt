package com.mateuszcholyn.wallet.expense.model

import com.mateuszcholyn.wallet.expense.activity.ALL_CATEGORIES
import java.util.*

data class AverageSearchCriteria(
        val categoryName: String,
        val beginDate: Calendar,
        val endDate: Calendar
) {
    fun isAllCategories() =
            ALL_CATEGORIES == categoryName
}



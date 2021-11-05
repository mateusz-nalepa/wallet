package com.mateuszcholyn.wallet.view.expense

import android.widget.TextView
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.util.toLocalDateTime

fun prepareExpenseSearchCriteria(
    categoryName: String,
    mBeginDate: TextView,
    mEndDate: TextView,
): ExpenseSearchCriteria {
    return ExpenseSearchCriteria(
        allCategories = categoryName == ALL_CATEGORIES,
        categoryName = if (categoryName == ALL_CATEGORIES) null else categoryName,
        beginDate = mBeginDate.toLocalDateTime(),
        endDate = mEndDate.toLocalDateTime()
    )
}
package com.mateuszcholyn.wallet.expense.model

import com.mateuszcholyn.wallet.expense.activity.ALL_CATEGORIES

fun AverageSearchCriteria.isAllCategories() =
        isAllCategories(categoryName)

fun ExpenseSearchCriteria.isAllCategories() =
        isAllCategories(categoryName)

fun isAllCategories(categoryName: String): Boolean =
        ALL_CATEGORIES == categoryName

package com.mateuszcholyn.wallet.domain.expense.model

import com.mateuszcholyn.wallet.domain.expense.activity.ALL_CATEGORIES

fun AverageSearchCriteria.isAllCategories() =
        isAllCategories(categoryName)

fun ExpenseSearchCriteria.isAllCategories() =
        isAllCategories(categoryName)

fun isAllCategories(categoryName: String): Boolean =
        ALL_CATEGORIES == categoryName

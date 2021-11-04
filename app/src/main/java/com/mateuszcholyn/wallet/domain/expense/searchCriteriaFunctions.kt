package com.mateuszcholyn.wallet.domain.expense

import com.mateuszcholyn.wallet.view.expense.ALL_CATEGORIES

fun AverageSearchCriteria.isAllCategories() =
    isAllCategories(categoryName)

fun ExpenseSearchCriteria.isAllCategories() =
    isAllCategories(categoryName)

fun isAllCategories(categoryName: String): Boolean =
    ALL_CATEGORIES == categoryName

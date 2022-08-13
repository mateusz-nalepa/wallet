package com.mateuszcholyn.wallet.manager.ext.getcategoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.manager.ExpenseAppManager

fun ExpenseAppManager.getCategoriesQuickSummaryUseCase(): QuickSummaryList {
    return this
        .expenseAppUseCases
        .getCategoriesQuickSummaryUseCase
        .invoke()
}

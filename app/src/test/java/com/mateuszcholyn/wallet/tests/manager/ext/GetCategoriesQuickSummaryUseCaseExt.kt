package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.getCategoriesQuickSummaryUseCase(): QuickSummaryList {
    return this
        .expenseAppUseCases
        .getCategoriesQuickSummaryUseCase
        .invoke()
}

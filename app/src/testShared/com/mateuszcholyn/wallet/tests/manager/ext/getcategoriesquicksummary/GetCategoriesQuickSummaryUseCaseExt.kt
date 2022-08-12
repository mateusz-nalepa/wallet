package com.mateuszcholyn.wallet.tests.manager.ext.getcategoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.getCategoriesQuickSummaryUseCase(): QuickSummaryList {
    return this
        .expenseAppUseCases
        .getCategoriesQuickSummaryUseCase
        .invoke()
}

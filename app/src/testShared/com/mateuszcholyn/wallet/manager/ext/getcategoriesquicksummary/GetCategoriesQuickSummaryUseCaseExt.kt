package com.mateuszcholyn.wallet.manager.ext.getcategoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryListV2
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.getCategoriesQuickSummaryUseCase(): QuickSummaryList =
    runBlocking {
        expenseAppUseCases
            .getCategoriesQuickSummaryUseCase
            .invoke()
    }

fun ExpenseAppManager.getCategoriesQuickSummaryUseCaseV2(): QuickSummaryListV2 =
    runBlocking {
        expenseAppUseCases
            .getCategoriesQuickSummaryUseCase
            .invokeV2()
    }
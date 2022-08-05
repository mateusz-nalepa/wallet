package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.searchServiceUseCase(): SearchServiceResult {
    return this
        .expenseAppUseCases
        .searchServiceUseCase
        .invoke()
}

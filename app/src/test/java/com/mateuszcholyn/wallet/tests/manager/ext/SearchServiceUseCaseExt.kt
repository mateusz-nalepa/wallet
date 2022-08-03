package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.searchservice.ExpensesList
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.searchServiceUseCase(): ExpensesList {
    return this
        .expenseAppUseCases
        .searchServiceUseCase
        .invoke()
}

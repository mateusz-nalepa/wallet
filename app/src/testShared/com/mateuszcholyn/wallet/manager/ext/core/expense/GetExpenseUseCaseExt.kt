package com.mateuszcholyn.wallet.manager.ext.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomExpenseId
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.getExpenseUseCase(
    scope: GetExpenseUseCaseScope.() -> Unit,
): ExpenseWithCategory {

    val getExpenseParameters =
        GetExpenseUseCaseScope()
            .apply(scope)
            .toGetExpenseParameters()

    return runBlocking {
        expenseAppUseCases
            .getExpenseUseCase
            .invoke(getExpenseParameters)
    }
}

class GetExpenseUseCaseScope {
    var expenseId: ExpenseId = randomExpenseId()

    fun toGetExpenseParameters(): ExpenseId =
        expenseId
}
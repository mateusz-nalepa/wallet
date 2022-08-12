package com.mateuszcholyn.wallet.tests.manager.ext.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.randomExpenseId

fun ExpenseAppManager.getExpenseUseCase(
    scope: GetExpenseUseCaseScope.() -> Unit,
): ExpenseV2WithCategory {

    val getExpenseParameters =
        GetExpenseUseCaseScope()
            .apply(scope)
            .toGetExpenseParameters()

    return this
        .expenseAppUseCases
        .getExpenseUseCase
        .invoke(getExpenseParameters)
}

class GetExpenseUseCaseScope {
    var expenseId: ExpenseId = randomExpenseId()

    fun toGetExpenseParameters(): ExpenseId =
        expenseId
}
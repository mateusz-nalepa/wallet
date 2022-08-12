package com.mateuszcholyn.wallet.tests.manager.ext.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.ExpenseRemovedStatus
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.randomExpenseId

fun ExpenseAppManager.removeExpenseUseCase(
    scope: RemoveExpenseUseCaseScope.() -> Unit,
): ExpenseRemovedStatus {

    val expenseId =
        RemoveExpenseUseCaseScope()
            .apply(scope)
            .toRemoveExpenseParameters()

    return this
        .expenseAppUseCases
        .removeExpenseUseCase
        .invoke(expenseId)
}

class RemoveExpenseUseCaseScope {
    var expenseId: ExpenseId = randomExpenseId()

    fun toRemoveExpenseParameters(): ExpenseId =
        expenseId
}
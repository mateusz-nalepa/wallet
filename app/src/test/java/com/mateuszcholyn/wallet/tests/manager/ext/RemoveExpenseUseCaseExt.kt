package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.randomExpenseId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.backend.usecase.ExpenseRemovedStatus

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
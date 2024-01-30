package com.mateuszcholyn.wallet.manager.ext.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.ExpenseRemovedStatus
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomExpenseId
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.removeExpenseUseCase(
    scope: RemoveExpenseUseCaseScope.() -> Unit,
): ExpenseRemovedStatus {

    val expenseId =
        RemoveExpenseUseCaseScope()
            .apply(scope)
            .toRemoveExpenseParameters()

    return runBlocking {
        expenseAppUseCases
            .removeExpenseUseCase
            .invoke(expenseId)
    }
}

class RemoveExpenseUseCaseScope {
    var expenseId: ExpenseId = randomExpenseId()

    fun toRemoveExpenseParameters(): ExpenseId =
        expenseId
}
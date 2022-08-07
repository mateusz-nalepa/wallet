package com.mateuszcholyn.wallet.app.usecase.core.expense

import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.app.usecase.UseCase

enum class ExpenseRemovedStatus {
    SUCCESS
}

class RemoveExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(expenseId: ExpenseId): ExpenseRemovedStatus {
        expenseCoreServiceAPI.remove(expenseId)
        return ExpenseRemovedStatus.SUCCESS
    }

}
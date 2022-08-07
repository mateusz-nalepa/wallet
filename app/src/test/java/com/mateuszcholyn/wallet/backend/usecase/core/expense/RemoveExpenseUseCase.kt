package com.mateuszcholyn.wallet.backend.usecase.core.expense

import com.mateuszcholyn.wallet.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.usecase.UseCase

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
package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.core.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.ExpenseId

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
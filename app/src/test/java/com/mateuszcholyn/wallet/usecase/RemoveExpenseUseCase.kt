package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.expensecore.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseId

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
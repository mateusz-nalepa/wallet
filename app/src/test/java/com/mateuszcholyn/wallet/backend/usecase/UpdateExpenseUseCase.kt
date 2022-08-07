package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.core.expense.Expense
import com.mateuszcholyn.wallet.backend.core.expense.ExpenseCoreServiceAPI

class UpdateExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(updateExpenseParameters: Expense): Expense =
        expenseCoreServiceAPI.update(updateExpenseParameters)

}
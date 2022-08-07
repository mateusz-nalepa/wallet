package com.mateuszcholyn.wallet.app.usecase.core.expense

import com.mateuszcholyn.wallet.app.backend.core.expense.Expense
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.app.usecase.UseCase

class UpdateExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(updateExpenseParameters: Expense): Expense =
        expenseCoreServiceAPI.update(updateExpenseParameters)

}
package com.mateuszcholyn.wallet.backend.usecase.core.expense

import com.mateuszcholyn.wallet.backend.core.expense.Expense
import com.mateuszcholyn.wallet.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.usecase.UseCase

class UpdateExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(updateExpenseParameters: Expense): Expense =
        expenseCoreServiceAPI.update(updateExpenseParameters)

}
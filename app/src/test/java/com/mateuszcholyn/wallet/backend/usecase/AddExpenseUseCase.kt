package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.expensecore.CreateExpenseParameters
import com.mateuszcholyn.wallet.backend.expensecore.Expense
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseCoreServiceAPI

class AddExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(createExpenseParameters: CreateExpenseParameters): Expense =
        expenseCoreServiceAPI.add(createExpenseParameters)

}
package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.expensecore.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.expensecore.Expense
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseCoreServiceAPI

class AddExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): Expense =
        expenseCoreServiceAPI.add(addExpenseParameters)

}
package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.core.expense.Expense
import com.mateuszcholyn.wallet.backend.core.expense.ExpenseCoreServiceAPI

class AddExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): Expense =
        expenseCoreServiceAPI.add(addExpenseParameters)

}
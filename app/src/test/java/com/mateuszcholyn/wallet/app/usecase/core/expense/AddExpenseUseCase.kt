package com.mateuszcholyn.wallet.app.usecase.core.expense

import com.mateuszcholyn.wallet.app.backend.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.app.backend.core.expense.Expense
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.app.usecase.UseCase

class AddExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): Expense =
        expenseCoreServiceAPI.add(addExpenseParameters)

}
package com.mateuszcholyn.wallet.backend.usecase.core.expense

import com.mateuszcholyn.wallet.backend.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.core.expense.Expense
import com.mateuszcholyn.wallet.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.usecase.UseCase

class AddExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): Expense =
        expenseCoreServiceAPI.add(addExpenseParameters)

}
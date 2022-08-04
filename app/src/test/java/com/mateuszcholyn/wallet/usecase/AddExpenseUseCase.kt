package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.core.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.core.Expense
import com.mateuszcholyn.wallet.backend.core.ExpenseCoreServiceAPI

class AddExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): Expense =
        expenseCoreServiceAPI.add(addExpenseParameters)

}
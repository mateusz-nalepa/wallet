package com.mateuszcholyn.wallet.newcode.app.usecase.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class AddExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): ExpenseV2 =
        expenseCoreService.add(addExpenseParameters)

}
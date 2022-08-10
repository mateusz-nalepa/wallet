package com.mateuszcholyn.wallet.newcode.app.usecase.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class UpdateExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(updateExpenseParameters: ExpenseV2): ExpenseV2 =
        expenseCoreService.update(updateExpenseParameters)

}
package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.core.Expense
import com.mateuszcholyn.wallet.backend.core.ExpenseCoreServiceAPI

class UpdateExpenseUseCase(
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(updateExpenseParameters: Expense): Expense =
        expenseCoreServiceAPI.update(updateExpenseParameters)

}
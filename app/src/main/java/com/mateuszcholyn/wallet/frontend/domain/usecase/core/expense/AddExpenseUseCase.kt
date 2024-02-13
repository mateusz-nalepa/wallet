package com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase


interface AddExpenseUseCase : UseCase {
    suspend fun invoke(addExpenseParameters: AddExpenseParameters): Expense
}


class DefaultAddExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : AddExpenseUseCase {

    override suspend fun invoke(addExpenseParameters: AddExpenseParameters): Expense =
        expenseCoreService.add(addExpenseParameters)

}
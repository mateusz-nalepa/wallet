package com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

interface UpdateExpenseUseCase : UseCase {
    suspend fun invoke(updatedExpense: Expense): Expense
}

class DefaultUpdateExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UpdateExpenseUseCase {

    override suspend fun invoke(updatedExpense: Expense): Expense =
        expenseCoreService.update(updatedExpense)

}
package com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

interface GetExpenseUseCase : UseCase {
    suspend fun invoke(expenseId: ExpenseId): Expense
}


class DefaultGetExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : GetExpenseUseCase {

    override suspend fun invoke(expenseId: ExpenseId): Expense =
        expenseCoreService.getByIdOrThrow(expenseId)

}
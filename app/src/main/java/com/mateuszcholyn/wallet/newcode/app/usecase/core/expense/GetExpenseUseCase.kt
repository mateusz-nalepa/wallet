package com.mateuszcholyn.wallet.newcode.app.usecase.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

// HODOR - write test for that
class GetExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(expenseId: ExpenseId): ExpenseV2WithCategory =
        expenseCoreService.getByIdOrThrow(expenseId)

}
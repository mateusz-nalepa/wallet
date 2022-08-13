package com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

class GetExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(expenseId: ExpenseId): ExpenseV2WithCategory =
        expenseCoreService.getByIdOrThrow(expenseId)

}
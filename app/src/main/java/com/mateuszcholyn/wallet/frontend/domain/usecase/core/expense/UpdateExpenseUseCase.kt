package com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

class UpdateExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UseCase {

    suspend fun invoke(updatedExpense: ExpenseV2): ExpenseV2 =
        expenseCoreService.update(updatedExpense)

}
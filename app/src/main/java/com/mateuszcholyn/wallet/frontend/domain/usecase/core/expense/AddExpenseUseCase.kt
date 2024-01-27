package com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense

import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase


// TODO:  w transakcji to powinno być
class AddExpenseUseCase(
    private val expenseCoreService: ExpenseCoreServiceAPI,
) : UseCase {

    fun invoke(addExpenseParameters: AddExpenseParameters): ExpenseV2 =
        expenseCoreService.add(addExpenseParameters)

}
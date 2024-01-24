package com.mateuszcholyn.wallet.manager.ext.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomDescription
import com.mateuszcholyn.wallet.manager.randomPaidAt
import java.math.BigDecimal
import java.time.Instant

fun ExpenseAppManager.addExpenseUseCase(
    scope: AddExpenseUseCaseScope.() -> Unit,
): ExpenseV2 {

    val addExpenseParameters =
        AddExpenseUseCaseScope()
            .apply(scope)
            .toAddExpenseParameters()

    return this
        .expenseAppUseCases
        .addExpenseUseCase
        .invoke(addExpenseParameters)
}

class AddExpenseUseCaseScope {
    var amount: BigDecimal = randomAmount()
    var description: String = randomDescription()
    var paidAt: Instant = randomPaidAt()
    var categoryId: CategoryId = randomCategoryId()

    fun toAddExpenseParameters(): AddExpenseParameters =
        AddExpenseParameters(
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )
}
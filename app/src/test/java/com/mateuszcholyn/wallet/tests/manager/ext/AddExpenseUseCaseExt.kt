package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.core.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.core.Expense
import com.mateuszcholyn.wallet.randomAmount
import com.mateuszcholyn.wallet.randomCategoryId
import com.mateuszcholyn.wallet.randomDescription
import com.mateuszcholyn.wallet.randomPaidAt
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.math.BigDecimal
import java.time.Instant

fun ExpenseAppManager.addExpenseUseCase(
    scope: AddExpenseUseCase.() -> Unit,
): Expense {

    val addExpenseParameters =
        AddExpenseUseCase()
            .apply(scope)
            .toAddExpenseParameters()

    return this
        .expenseAppUseCases
        .addExpenseUseCase
        .invoke(addExpenseParameters)
}

class AddExpenseUseCase {
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
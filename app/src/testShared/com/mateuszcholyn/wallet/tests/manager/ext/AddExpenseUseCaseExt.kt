package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.Expense
import com.mateuszcholyn.wallet.tests.manager.randomAmount
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.randomDescription
import com.mateuszcholyn.wallet.tests.manager.randomPaidAt
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.math.BigDecimal
import java.time.LocalDateTime

fun ExpenseAppManager.addExpenseUseCase(
    scope: AddExpenseUseCaseScope.() -> Unit,
): Expense {

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
    var paidAt: LocalDateTime = randomPaidAt()
    var categoryId: CategoryId = randomCategoryId()

    fun toAddExpenseParameters(): AddExpenseParameters =
        AddExpenseParameters(
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )
}
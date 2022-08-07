package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.*
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.app.backend.core.expense.Expense
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.math.BigDecimal
import java.time.LocalDateTime

fun ExpenseAppManager.updateExpenseUseCase(
    scope: UpdateExpenseUseCaseScope.() -> Unit,
): Expense {

    val addExpenseParameters =
        UpdateExpenseUseCaseScope()
            .apply(scope)
            .toExpense()

    return this
        .expenseAppUseCases
        .updateExpenseUseCase
        .invoke(addExpenseParameters)
}

class UpdateExpenseUseCaseScope {
    var existingExpenseId: ExpenseId = randomExpenseId()
    var newAmount: BigDecimal = randomAmount()
    var newDescription: String = randomDescription()
    var newPaidAt: LocalDateTime = randomPaidAt()
    var newCategoryId: CategoryId = randomCategoryId()

    fun toExpense(): Expense =
        Expense(
            id = existingExpenseId,
            amount = newAmount,
            description = newDescription,
            paidAt = newPaidAt,
            categoryId = newCategoryId,
        )
}
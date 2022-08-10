package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.tests.manager.*
import java.math.BigDecimal
import java.time.LocalDateTime

fun ExpenseAppManager.updateExpenseUseCase(
    scope: UpdateExpenseUseCaseScope.() -> Unit,
): ExpenseV2 {

    val updateExpenseUseCaseParameters =
        UpdateExpenseUseCaseScope()
            .apply(scope)
            .toExpense()

    return this
        .expenseAppUseCases
        .updateExpenseUseCase
        .invoke(updateExpenseUseCaseParameters)
}

class UpdateExpenseUseCaseScope {
    var existingExpenseId: ExpenseId = randomExpenseId()
    var newAmount: BigDecimal = randomAmount()
    var newDescription: String = randomDescription()
    var newPaidAt: LocalDateTime = randomPaidAt()
    var newCategoryId: CategoryId = randomCategoryId()

    fun toExpense(): ExpenseV2 =
        ExpenseV2(
            id = existingExpenseId,
            amount = newAmount,
            description = newDescription,
            paidAt = newPaidAt,
            categoryId = newCategoryId,
        )
}
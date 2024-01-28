package com.mateuszcholyn.wallet.manager.ext.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomDescription
import com.mateuszcholyn.wallet.manager.randomExpenseId
import com.mateuszcholyn.wallet.manager.randomPaidAt
import java.math.BigDecimal
import java.time.Instant

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
    var newPaidAt: Instant = randomPaidAt()
    // TODO: this should be lateinitVar??
    var newCategoryId: CategoryId = randomCategoryId()

    fun toExpense(): ExpenseV2 =
        ExpenseV2(
            expenseId = existingExpenseId,
            amount = newAmount,
            description = newDescription,
            paidAt = newPaidAt,
            categoryId = newCategoryId,
        )
}
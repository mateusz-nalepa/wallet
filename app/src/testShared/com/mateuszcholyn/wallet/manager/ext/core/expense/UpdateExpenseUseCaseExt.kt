package com.mateuszcholyn.wallet.manager.ext.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomAmount
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomDescription
import com.mateuszcholyn.wallet.manager.randomExpenseId
import com.mateuszcholyn.wallet.manager.randomPaidAt
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import java.time.Instant

fun ExpenseAppManager.updateExpenseUseCase(
    scope: UpdateExpenseUseCaseScope.() -> Unit,
): Expense {

    val updateExpenseUseCaseParameters =
        UpdateExpenseUseCaseScope()
            .apply(scope)
            .toExpense()

    return runBlocking {
        expenseAppUseCases
            .updateExpenseUseCase
            .invoke(updateExpenseUseCaseParameters)
    }
}

class UpdateExpenseUseCaseScope {
    var existingExpenseId: ExpenseId = randomExpenseId()
    var newAmount: BigDecimal = randomAmount()
    var newDescription: String = randomDescription()
    var newPaidAt: Instant = randomPaidAt()

    var newCategoryId: CategoryId = randomCategoryId()

    fun toExpense(): Expense =
        Expense(
            expenseId = existingExpenseId,
            amount = newAmount,
            description = newDescription,
            paidAt = newPaidAt,
            categoryId = newCategoryId,
        )
}
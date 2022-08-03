package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.expensecore.Expense
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.time.Instant

fun ExpenseAppManager.validate(
    expenseId: ExpenseId,
    validateBlock: SimpleExpenseValidator.() -> Unit,
) {
    val expense = this.expenseAppDependencies.expenseRepository.getById(expenseId)
    requireNotNull(expense) { "Expense with id $expenseId should exist" }
    expense.validate(validateBlock)
}


fun Expense.validate(validateBlock: SimpleExpenseValidator.() -> Unit) {
    SimpleExpenseValidator(this).apply(validateBlock)
}

class SimpleExpenseValidator(
    private val expense: Expense,
) {
    fun paidAtEqualTo(expectedPaidAt: Instant) {
        assert(expense.paidAt == expectedPaidAt) {
            "Expected paidAt is: $expectedPaidAt. Actual: ${expense.paidAt}"
        }
    }
}



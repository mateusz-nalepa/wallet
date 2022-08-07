package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.ExpenseRemovedStatus

fun ExpenseRemovedStatus.validate(validateBlock: SimpleExpenseRemovedStatusValidator.() -> Unit) {
    SimpleExpenseRemovedStatusValidator(this).apply(validateBlock)
}

class SimpleExpenseRemovedStatusValidator(
    private val expenseRemovedStatus: ExpenseRemovedStatus,
) {
    fun statusEqualTo(expectedExpenseRemovedStatus: ExpenseRemovedStatus) {
        assert(expenseRemovedStatus == expectedExpenseRemovedStatus) {
            "Expected expenseRemovedStatus is: $expectedExpenseRemovedStatus. Actual: $expenseRemovedStatus"
        }
    }
}

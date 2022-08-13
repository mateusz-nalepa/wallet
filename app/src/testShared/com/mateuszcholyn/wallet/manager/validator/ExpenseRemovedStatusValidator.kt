package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.ExpenseRemovedStatus

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

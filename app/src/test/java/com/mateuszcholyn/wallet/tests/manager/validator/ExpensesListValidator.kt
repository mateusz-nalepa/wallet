package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.searchservice.ExpensesList


fun ExpensesList.validate(validateBlock: SimpleExpensesListValidator.() -> Unit) {
    SimpleExpensesListValidator(this).apply(validateBlock)
}

class SimpleExpensesListValidator(
    private val expensesList: ExpensesList,
) {
    fun hasNumberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        assert(expensesList.expenses.size == expectedNumberOfExpenses) {
            "Expected number of expenses should be: $expectedNumberOfExpenses. " +
                    "Actual: ${expensesList.expenses.size}"
        }
    }
}


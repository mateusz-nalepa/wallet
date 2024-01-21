package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.manager.ExpenseAppManager

fun ExpenseAppManager.validate(
    validateBlock: ExpenseAppManagerValidator.() -> Unit,
) {
    ExpenseAppManagerValidator(this).apply(validateBlock)
}

class ExpenseAppManagerValidator(
    private val expenseAppManager: ExpenseAppManager
) {
    fun numberOfExpensesInExpenseCoreEqualTo(expectedNumberOfExpenses: Int) {
        val actualNumberOfExpenses =
            expenseAppManager
                .expenseAppDependencies
                .expenseRepositoryV2
                .getAllExpenses()
                .size

        assert(actualNumberOfExpenses == expectedNumberOfExpenses) {
            "Expected number of expenses should be: $expectedNumberOfExpenses. Actual: $actualNumberOfExpenses"
        }
    }
}
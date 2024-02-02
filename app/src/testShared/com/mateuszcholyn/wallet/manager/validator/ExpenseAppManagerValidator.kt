package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.validate(
    validateBlock: ExpenseAppManagerValidator.() -> Unit,
) {
    ExpenseAppManagerValidator(this).apply(validateBlock)
}

class ExpenseAppManagerValidator(
    private val expenseAppManager: ExpenseAppManager
) {
    fun numberOfCoreExpensesEqualTo(expectedNumberOfExpenses: Int) {
        runBlocking {
            val actualNumberOfExpenses =
                expenseAppManager
                    .expenseAppDependencies
                    .expenseRepository
                    .getAllExpenses()
                    .size

            assert(actualNumberOfExpenses == expectedNumberOfExpenses) {
                "Expected number of expenses should be: $expectedNumberOfExpenses. Actual: $actualNumberOfExpenses"
            }
        }
    }

    fun numberOfCoreCategoriesEqualTo(expectedNumberOfCategories: Int) {
        runBlocking {
            val actualNumberOfCategories =
                expenseAppManager
                    .expenseAppDependencies
                    .categoryRepository
                    .getAllCategories()
                    .size

            assert(actualNumberOfCategories == expectedNumberOfCategories) {
                "Expected number of expenses should be: $expectedNumberOfCategories. Actual: $actualNumberOfCategories"
            }
        }
    }

}

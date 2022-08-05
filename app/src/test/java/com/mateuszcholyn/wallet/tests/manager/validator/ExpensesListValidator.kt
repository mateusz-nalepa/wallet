package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import java.math.BigDecimal
import java.math.RoundingMode


fun SearchServiceResult.validate(validateBlock: SimpleExpensesListValidator.() -> Unit) {
    SimpleExpensesListValidator(this).apply(validateBlock)
}

class SimpleExpensesListValidator(
    private val searchServiceResult: SearchServiceResult,
) {
    fun numberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        assert(searchServiceResult.expenses.size == expectedNumberOfExpenses) {
            "Expected number of expenses should be: $expectedNumberOfExpenses. " +
                    "Actual: ${searchServiceResult.expenses.size}"
        }
    }

    fun averageExpenseIs(expectedAverageExpense: BigDecimal) {
        val roundedExpectedAverageExpense =
            expectedAverageExpense.divide(BigDecimal.ONE, 2, RoundingMode.HALF_UP)

        assert(searchServiceResult.averageExpenseResult.averageAmount == roundedExpectedAverageExpense) {
            "Expected average expense amount should be: $roundedExpectedAverageExpense. " +
                    "Actual: ${searchServiceResult.averageExpenseResult.averageAmount}"
        }
    }

    fun numberOfDaysEqualTo(expectedNumberOfDays: Int) {
        assert(searchServiceResult.averageExpenseResult.days == expectedNumberOfDays) {
            "Expected number of days should be: $expectedNumberOfDays. " +
                    "Actual: ${searchServiceResult.averageExpenseResult.days}"
        }
    }
}


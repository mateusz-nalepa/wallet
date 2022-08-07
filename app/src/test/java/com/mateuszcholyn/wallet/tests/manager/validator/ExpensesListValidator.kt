package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.tests.manager.ExpenseScope
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime


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

    fun expenseIndex(
        expenseIndex: Int,
        validationBlock: SimpleSingleExpenseAddedEventValidator.() -> Unit,
    ) {
        SimpleSingleExpenseAddedEventValidator(
            expenseIndex = expenseIndex,
            expenseAddedEvent = searchServiceResult.expenses[expenseIndex],
        ).validationBlock()
    }
}

class SimpleSingleExpenseAddedEventValidator(
    private val expenseIndex: Int,
    private val expenseAddedEvent: ExpenseAddedEvent,
) {

    fun equalTo(expenseScope: ExpenseScope) {
        paidAtEqualTo(expenseScope.paidAt)
        amountEqualTo(expenseScope.amount)
        idEqualTo(expenseScope.expenseId)
    }

    fun paidAtEqualTo(expectedPaidAt: LocalDateTime) {
        assert(expenseAddedEvent.paidAt == expectedPaidAt) {
            "Expense with index $expenseIndex should have paid at equal to: $expectedPaidAt. " +
                    "Actual: ${expenseAddedEvent.paidAt}"
        }
    }

    fun amountEqualTo(expectedAmount: BigDecimal) {
        assert(expenseAddedEvent.amount == expectedAmount) {
            "Expense with index $expenseIndex should have amount equal to: $expectedAmount. " +
                    "Actual: ${expenseAddedEvent.amount}"
        }
    }

    fun idEqualTo(expectedId: ExpenseId) {
        assert(expenseAddedEvent.expenseId == expectedId) {
            "Expense with index $expenseIndex should have id equal to: $expectedId. " +
                    "Actual: ${expenseAddedEvent.expenseId}"
        }
    }

}

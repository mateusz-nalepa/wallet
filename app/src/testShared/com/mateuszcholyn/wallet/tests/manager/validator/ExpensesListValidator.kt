package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchSingleResult
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
            searchSingleResult = searchServiceResult.expenses[expenseIndex],
        ).validationBlock()
    }
}

class SimpleSingleExpenseAddedEventValidator(
    private val expenseIndex: Int,
    private val searchSingleResult: SearchSingleResult,
) {

    fun equalTo(expenseScope: ExpenseScope) {
        paidAtEqualTo(expenseScope.paidAt)
        amountEqualTo(expenseScope.amount)
        idEqualTo(expenseScope.expenseId)
    }

    fun paidAtEqualTo(expectedPaidAt: LocalDateTime) {
        assert(searchSingleResult.paidAt == expectedPaidAt) {
            "Expense with index $expenseIndex should have paid at equal to: $expectedPaidAt. " +
                    "Actual: ${searchSingleResult.paidAt}"
        }
    }

    fun amountEqualTo(expectedAmount: BigDecimal) {
        assert(searchSingleResult.amount == expectedAmount) {
            "Expense with index $expenseIndex should have amount equal to: $expectedAmount. " +
                    "Actual: ${searchSingleResult.amount}"
        }
    }

    fun idEqualTo(expectedId: ExpenseId) {
        assert(searchSingleResult.expenseId == expectedId) {
            "Expense with index $expenseIndex should have id equal to: $expectedId. " +
                    "Actual: ${searchSingleResult.expenseId}"
        }
    }

    fun categoryIdEqualTo(expectedCategoryId: CategoryId) {
        assert(searchSingleResult.categoryId == expectedCategoryId) {
            "Expense with index $expenseIndex should have category id equal to: $expectedCategoryId. " +
                    "Actual: ${searchSingleResult.categoryId}"
        }
    }

    fun categoryNameEqualTo(expectedCategoryName: String) {
        assert(searchSingleResult.categoryName == expectedCategoryName) {
            "Expense with index $expenseIndex should have categoryName equal to: $expectedCategoryName. " +
                    "Actual: ${searchSingleResult.categoryName}"
        }
    }

}

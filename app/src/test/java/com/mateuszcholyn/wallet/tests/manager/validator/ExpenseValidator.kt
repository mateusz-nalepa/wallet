package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.core.Expense
import com.mateuszcholyn.wallet.backend.core.ExpenseId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.math.BigDecimal
import java.time.LocalDateTime

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
    fun paidAtEqualTo(expectedPaidAt: LocalDateTime) {
        assert(expense.paidAt == expectedPaidAt) {
            "Expected paidAt should be: $expectedPaidAt. Actual: ${expense.paidAt}"
        }
    }

    fun descriptionEqualTo(expectedDescription: String) {
        assert(expense.description == expectedDescription) {
            "Expected description should be: $expectedDescription. Actual: ${expense.description}"
        }
    }

    fun amountEqualTo(expectedAmount: BigDecimal) {
        assert(expense.amount == expectedAmount) {
            "Expected amount should be: $expectedAmount. Actual: ${expense.amount}"
        }
    }

    fun categoryIdEqualTo(expectedCategoryId: CategoryId) {
        assert(expense.categoryId == expectedCategoryId) {
            "Expected category Id should be: $expectedCategoryId. Actual: ${expense.categoryId}"
        }
    }
}



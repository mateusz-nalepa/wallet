package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.math.BigDecimal
import java.time.LocalDateTime

fun ExpenseAppManager.validate(
    expenseId: ExpenseId,
    validateBlock: SimpleExpenseValidator.() -> Unit,
) {
    val expense = this.expenseAppDependencies.expenseRepositoryV2.getById(expenseId)
    requireNotNull(expense) { "Expense with id $expenseId should exist" }
    expense.validate(validateBlock)
}


fun ExpenseV2.validate(validateBlock: SimpleExpenseValidator.() -> Unit) {
    SimpleExpenseValidator(this).apply(validateBlock)
}

class SimpleExpenseValidator(
    private val expense: ExpenseV2,
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
        assert(expense.amount.toString() == expectedAmount.toString()) {
            "Expected amount should be: $expectedAmount. Actual: ${expense.amount}"
        }
    }

    fun categoryIdEqualTo(expectedCategoryId: CategoryId) {
        assert(expense.categoryId == expectedCategoryId) {
            "Expected category Id should be: $expectedCategoryId. Actual: ${expense.categoryId}"
        }
    }
}



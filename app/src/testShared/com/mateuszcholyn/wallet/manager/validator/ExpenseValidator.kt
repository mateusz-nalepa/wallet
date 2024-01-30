package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.validator.LocalDateTimeValidator.assertInstant
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal
import java.time.Instant

fun ExpenseAppManager.validate(
    expenseId: ExpenseId,
    validateBlock: SimpleExpenseValidator.() -> Unit,
) {
    runBlocking {
        val expense = expenseAppDependencies.expenseRepositoryV2.getById(expenseId)
        requireNotNull(expense) { "Expense with id $expenseId should exist" }
        expense.validate(validateBlock)
    }
}


fun ExpenseV2.validate(validateBlock: SimpleExpenseValidator.() -> Unit) {
    SimpleExpenseValidator(this).apply(validateBlock)
}

class SimpleExpenseValidator(
    private val expense: ExpenseV2,
) {

    fun isSameAsExpenseFromDatabase(
        expenseScope: ExpenseScope,
        categoryId: CategoryId,
    ) {
        idIsEqualTo(expenseScope.expenseId)
        paidAtEqualTo(expenseScope.paidAt)
        descriptionEqualTo(expenseScope.description)
        amountEqualTo(expenseScope.amount)
        categoryIdEqualTo(categoryId)
    }

    fun isSameAsExpenseFromBackup(
        backupExpense: BackupWalletV1.BackupCategoryV1.BackupExpenseV1,
        categoryId: CategoryId,
    ) {
        idIsEqualTo(ExpenseId(backupExpense.expenseId))
        paidAtEqualTo(InstantConverter.toInstant(backupExpense.paidAt))
        descriptionEqualTo(backupExpense.description)
        amountEqualTo(backupExpense.amount)
        categoryIdEqualTo(categoryId)
    }

    fun idIsEqualTo(expectedExpenseId: ExpenseId) {
        assert(expense.expenseId == expectedExpenseId) {
            "Expected expenseId should be: $expectedExpenseId. Actual: ${expense.expenseId}"
        }
    }

    fun paidAtEqualTo(expectedPaidAt: Instant) {
        assertInstant(expense.paidAt, expectedPaidAt) {
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



package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseScope
import com.mateuszcholyn.wallet.manager.validator.LocalDateTimeValidator.assertInstant

fun ExpenseV2WithCategory.validate(
    validateBlock: ExpenseV2WithCategoryValidator.() -> Unit,
) {
    ExpenseV2WithCategoryValidator(
        expenseV2WithCategory = this,
    ).apply(validateBlock)
}


class ExpenseV2WithCategoryValidator(
    private val expenseV2WithCategory: ExpenseV2WithCategory,
) {
    fun equalTo(
        categoryScope: CategoryScope,
        expenseScope: ExpenseScope,
    ) {
        assert(expenseV2WithCategory.categoryId == categoryScope.categoryId) {
            "Expected categoryId is: $${categoryScope.categoryId}. " +
                    "Actual: ${expenseV2WithCategory.categoryId}"
        }

        assert(expenseV2WithCategory.categoryName == categoryScope.categoryName) {
            "Expected category name is: ${categoryScope.categoryName}. " +
                    "Actual: ${expenseV2WithCategory.categoryName}"
        }

        assert(expenseV2WithCategory.expenseId == expenseScope.expenseId) {
            "Expected expenseId is: ${expenseScope.expenseId}. " +
                    "Actual: ${expenseV2WithCategory.expenseId}"
        }

        assert(expenseV2WithCategory.description == expenseScope.description) {
            "Expected description is: ${expenseScope.description}. " +
                    "Actual: ${expenseV2WithCategory.description}"
        }

        assertInstant(expenseV2WithCategory.paidAt, expenseScope.paidAt) {
            "Expected paidAt is: ${expenseScope.paidAt}. " +
                    "Actual: ${expenseV2WithCategory.paidAt}"
        }

        assert(expenseV2WithCategory.amount == expenseScope.amount) {
            "Expected amount is: ${expenseScope.amount}. " +
                    "Actual: ${expenseV2WithCategory.amount}"
        }
    }
}

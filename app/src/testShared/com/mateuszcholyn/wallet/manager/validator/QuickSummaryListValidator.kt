package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId

fun QuickSummaryList.validate(
    validationBlock: SimpleQuickSummaryListValidator.() -> Unit,
) {
    SimpleQuickSummaryListValidator(this).apply(validationBlock)
}

class SimpleQuickSummaryListValidator(
    private val quickSummaryList: QuickSummaryList,
) {
    fun categoryId(
        categoryId: CategoryId,
        validationBlock: SimpleQuickSummaryValidator.() -> Unit,
    ) {
        val quickSummaryOrNull =
            quickSummaryList
                .quickSummaries
                .find { it.categoryId == categoryId }
        requireNotNull(quickSummaryOrNull) { "Quick summary for category with id: $categoryId should be present" }

        SimpleQuickSummaryValidator(quickSummaryOrNull).apply(validationBlock)
    }

    fun categoryIdDoesNotExist(
        categoryId: CategoryId,
    ) {
        val quickSummaryOrNull =
            quickSummaryList
                .quickSummaries
                .find { it.categoryId == categoryId }

        assert(quickSummaryOrNull == null) {
            "Category with given id $categoryId should not be present in quick summary list"
        }
    }
}


class SimpleQuickSummaryValidator(
    private val categoryQuickSummary: CategoryQuickSummary,
) {
    fun doesNotHaveExpenses() {
        hasNumberOfExpensesEqualTo(0)
    }

    fun hasNumberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        assert(categoryQuickSummary.numberOfExpenses == expectedNumberOfExpenses.toLong()) {
            "Expected number of expenses for categoryId: ${categoryQuickSummary.categoryId} " +
                    "should be: $expectedNumberOfExpenses. " +
                    "Actual: ${categoryQuickSummary.numberOfExpenses}"
        }
    }

    fun hasName(expectedCategoryName: String) {
        assert(categoryQuickSummary.categoryName == expectedCategoryName) {
            "Expected categoryName for categoryId: ${categoryQuickSummary.categoryId} " +
                    "should be: $expectedCategoryName. " +
                    "Actual: ${categoryQuickSummary.categoryName}"
        }
    }
}


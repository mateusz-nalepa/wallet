package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.QuickSummary
import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId

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
    private val quickSummary: QuickSummary,
) {
    fun doesNotHaveExpenses() {
        hasNumberOfExpensesEqualTo(0)
    }

    fun hasNumberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        assert(quickSummary.numberOfExpenses == expectedNumberOfExpenses) {
            "Expected number of expenses for categoryId: ${quickSummary.categoryId} " +
                    "should be: $expectedNumberOfExpenses. " +
                    "Actual: ${quickSummary.numberOfExpenses}"
        }
    }
}


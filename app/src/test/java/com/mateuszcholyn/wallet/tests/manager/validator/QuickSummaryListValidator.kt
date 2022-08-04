package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.QuickSummary
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.core.CategoryId

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
        val quickSummary =
            quickSummaryList
                .quickSummaries
                .find { it.categoryId == categoryId }
        requireNotNull(quickSummary) { "Quick summary for category with id: $categoryId should be present" }

        SimpleQuickSummaryValidator(quickSummary).apply(validationBlock)
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


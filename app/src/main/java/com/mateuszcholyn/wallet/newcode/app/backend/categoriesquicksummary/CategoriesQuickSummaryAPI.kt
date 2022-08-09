package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.events.*


interface CategoriesQuickSummaryAPI {
    fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent)
    fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent)
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent)
    fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
    fun getQuickSummary(): QuickSummaryList
}

data class QuickSummaryList(
    val quickSummaries: List<QuickSummary>,
)

data class QuickSummary(
    val categoryId: CategoryId,
    val numberOfExpenses: Long,
) {
    fun incWithOne(): QuickSummary =
        this.copy(
            numberOfExpenses = numberOfExpenses + 1,
        )

    fun decWithOne(): QuickSummary =
        this.copy(
            numberOfExpenses = numberOfExpenses - 1,
        )
}
package com.mateuszcholyn.wallet.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent


interface CategoriesQuickSummaryAPI {
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun getQuickSummary(): QuickSummaryList
}

data class QuickSummaryList(
    val quickSummaries: List<QuickSummary>,
)

data class QuickSummary(
    val categoryId: CategoryId,
    val numberOfExpenses: Int,
)
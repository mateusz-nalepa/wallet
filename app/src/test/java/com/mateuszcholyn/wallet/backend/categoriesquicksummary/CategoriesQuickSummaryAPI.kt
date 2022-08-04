package com.mateuszcholyn.wallet.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent


interface CategoriesQuickSummaryAPI {
    fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent)
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun getQuickSummary(): QuickSummaryList
    fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
}

data class QuickSummaryList(
    val quickSummaries: List<QuickSummary>,
)

data class QuickSummary(
    val categoryId: CategoryId,
    val numberOfExpenses: Int,
)
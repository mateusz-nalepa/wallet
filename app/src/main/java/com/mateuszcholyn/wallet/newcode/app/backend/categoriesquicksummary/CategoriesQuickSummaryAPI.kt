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
    val quickSummaries: List<CategoryQuickSummary>,
)

data class CategoryQuickSummary(
    val categoryId: CategoryId,
    val categoryName: String,
    val numberOfExpenses: Long,
)

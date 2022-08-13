package com.mateuszcholyn.wallet.backend.api.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent


interface CategoriesQuickSummaryAPI {
    fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent)
    fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent)
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent)
    fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
    fun getQuickSummary(): QuickSummaryList
    fun removeAll()
}

data class QuickSummaryList(
    val quickSummaries: List<CategoryQuickSummary>,
)

data class CategoryQuickSummary(
    val categoryId: CategoryId,
    val categoryName: String,
    val numberOfExpenses: Long,
)

package com.mateuszcholyn.wallet.backend.api.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomCategoryName
import com.mateuszcholyn.wallet.manager.randomLong


interface CategoriesQuickSummaryAPI {
    suspend fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent)
    suspend fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent)
    suspend fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    suspend fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent)
    suspend fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
    suspend fun getQuickSummary(): QuickSummaryList
    suspend fun removeAll()
}

data class QuickSummaryList(
    val quickSummaries: List<CategoryQuickSummary>,
)

data class CategoryQuickSummary(
    val categoryId: CategoryId,
    val categoryName: String,
    val numberOfExpenses: Long,
)

fun randomCategoryQuickSummary(
    categoryName: String = randomCategoryName(),
    numberOfExpenses: Long = randomLong(),
): CategoryQuickSummary =
    CategoryQuickSummary(
        randomCategoryId(),
        categoryName,
        numberOfExpenses,
    )

package com.mateuszcholyn.wallet.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent


class CategoriesQuickSummaryIMPL(
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
) : CategoriesQuickSummaryAPI {
    override fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent) {
        categoryAddedEvent
            .toQuickSummary()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

    override fun handleCategoryRemoved(categoryRemovedEvent: CategoryRemovedEvent) {
        categoriesQuickSummaryRepository.remove(categoryRemovedEvent.categoryId)
    }

    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        expenseAddedEvent
            .toQuickSummary()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

    override fun getQuickSummary(): QuickSummaryList =
        categoriesQuickSummaryRepository
            .getQuickSummaries()
            .let { QuickSummaryList(it) }

    override fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent) {
        expenseRemovedEvent
            .toQuickSummary()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

    private fun CategoryAddedEvent.toQuickSummary(): QuickSummary =
        QuickSummary(
            categoryId = categoryId,
            numberOfExpenses = 0,
        )

    private fun ExpenseAddedEvent.toQuickSummary(): QuickSummary =
        QuickSummary(
            categoryId = categoryId,
            numberOfExpenses = 1,
        )

    private fun ExpenseRemovedEvent.toQuickSummary(): QuickSummary =
        QuickSummary(
            categoryId = categoryId,
            numberOfExpenses = -1,
        )
}

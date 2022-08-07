package com.mateuszcholyn.wallet.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.app.backend.events.*


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

    override fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent) {
        expenseUpdatedEvent
            .toDecreaseNumberOfExpenses()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }

        expenseUpdatedEvent
            .toIncreaseNumberOfExpenses()
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

    private fun ExpenseUpdatedEvent.toDecreaseNumberOfExpenses(): QuickSummary =
        QuickSummary(
            categoryId = this.oldCategoryId,
            numberOfExpenses = -1,
        )

    private fun ExpenseUpdatedEvent.toIncreaseNumberOfExpenses(): QuickSummary =
        QuickSummary(
            categoryId = this.newCategoryId,
            numberOfExpenses = 1,
        )
}

package com.mateuszcholyn.wallet.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent


interface CategoriesQuickSummaryRepository {
    fun saveQuickSummary(quickSummary: QuickSummary): QuickSummary
    fun getQuickSummaries(): List<QuickSummary>
}

class InMemoryCategoriesQuickSummaryRepository : CategoriesQuickSummaryRepository {
    private val storage: MutableMap<CategoryId, QuickSummary> = mutableMapOf()

    override fun saveQuickSummary(quickSummary: QuickSummary): QuickSummary {
        val categoryId = quickSummary.categoryId
        val actualNumberOfExpenses = storage[categoryId]?.numberOfExpenses ?: 0
        val newNumberOfExpenses = quickSummary.numberOfExpenses + actualNumberOfExpenses
        storage[categoryId] = QuickSummary(categoryId, newNumberOfExpenses)
        return quickSummary
    }

    override fun getQuickSummaries(): List<QuickSummary> =
        storage.values.toList()
}

class CategoriesQuickSummaryIMPL(
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
) : CategoriesQuickSummaryAPI {
    override fun handleCategoryAdded(categoryAddedEvent: CategoryAddedEvent) {
        categoryAddedEvent
            .toQuickSummary()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
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

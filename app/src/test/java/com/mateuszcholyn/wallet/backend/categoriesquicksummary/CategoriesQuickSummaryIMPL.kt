package com.mateuszcholyn.wallet.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent


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
    override fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent) {
        expenseAddedEvent
            .toQuickSummary()
            .also { categoriesQuickSummaryRepository.saveQuickSummary(it) }
    }

    override fun getQuickSummary(): QuickSummaryList =
        categoriesQuickSummaryRepository
            .getQuickSummaries()
            .let { QuickSummaryList(it) }

    private fun ExpenseAddedEvent.toQuickSummary(): QuickSummary =
        QuickSummary(
            categoryId = categoryId,
            numberOfExpenses = 1,
        )
}

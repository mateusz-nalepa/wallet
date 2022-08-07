package com.mateuszcholyn.wallet.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId

interface CategoriesQuickSummaryRepository {
    fun saveQuickSummary(quickSummary: QuickSummary): QuickSummary
    fun getQuickSummaries(): List<QuickSummary>
    fun remove(categoryId: CategoryId)
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

    override fun remove(categoryId: CategoryId) {
        storage.remove(categoryId)
    }
}
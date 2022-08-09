package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

class InMemoryCategoriesQuickSummaryRepository : CategoriesQuickSummaryRepository {
    private val storage: MutableMap<CategoryId, QuickSummary> = mutableMapOf()

    override fun saveQuickSummary(quickSummary: QuickSummary): QuickSummary {
        storage[quickSummary.categoryId] = quickSummary
        return quickSummary
    }

    override fun getQuickSummaries(): List<QuickSummary> =
        storage.values.toList()

    override fun remove(categoryId: CategoryId) {
        storage.remove(categoryId)
    }

    override fun findByCategoryId(categoryId: CategoryId): QuickSummary? =
        storage[categoryId]
}
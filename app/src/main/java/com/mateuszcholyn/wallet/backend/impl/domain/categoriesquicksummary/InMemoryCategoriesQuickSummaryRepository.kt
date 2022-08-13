package com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.impl.domain.util.removeAll

class InMemoryCategoriesQuickSummaryRepository : CategoriesQuickSummaryRepository {
    private val storage: MutableMap<CategoryId, CategoryQuickSummaryResult> = mutableMapOf()

    override fun saveQuickSummaryResult(
        categoryQuickSummaryResult: CategoryQuickSummaryResult,
    ): CategoryQuickSummaryResult {
        storage[categoryQuickSummaryResult.categoryId] = categoryQuickSummaryResult
        return categoryQuickSummaryResult
    }

    override fun getQuickSummaries(): List<CategoryQuickSummaryResult> =
        storage.values.toList()

    override fun remove(categoryId: CategoryId) {
        storage.remove(categoryId)
    }

    override fun findByCategoryId(categoryId: CategoryId): CategoryQuickSummaryResult? =
        storage[categoryId]

    override fun removeAll() {
        storage.removeAll { it.categoryId }
    }
}

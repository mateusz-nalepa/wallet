package com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.impl.domain.util.removeAll

class InMemoryCategoriesQuickSummaryRepository : CategoriesQuickSummaryRepository {
    private val storage: MutableMap<CategoryId, CategoryQuickSummaryResult> = mutableMapOf()

    override suspend fun saveQuickSummaryResult(
        categoryQuickSummaryResult: CategoryQuickSummaryResult,
    ): CategoryQuickSummaryResult {
        storage[categoryQuickSummaryResult.categoryId] = categoryQuickSummaryResult
        return categoryQuickSummaryResult
    }

    override suspend fun getQuickSummaries(): List<CategoryQuickSummaryResult> =
        storage.values.toList()

    override suspend fun remove(categoryId: CategoryId) {
        storage.remove(categoryId)
    }

    override suspend fun findByCategoryId(categoryId: CategoryId): CategoryQuickSummaryResult? =
        storage[categoryId]

    override suspend fun removeAll() {
        storage.removeAll { it.categoryId }
    }
}

package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

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

fun <KEY, ENTRY> MutableMap<KEY, ENTRY>.removeAll(
    keyFromEntry: (ENTRY) -> KEY,
) {
    val keys = this.values.toList().map { keyFromEntry(it) }

    keys.forEach { key ->
        this.remove(key)
    }
}

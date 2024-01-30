package com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId

interface CategoriesQuickSummaryRepository {
    suspend fun saveQuickSummaryResult(categoryQuickSummaryResult: CategoryQuickSummaryResult): CategoryQuickSummaryResult
    suspend fun getQuickSummaries(): List<CategoryQuickSummaryResult>
    suspend fun remove(categoryId: CategoryId)
    suspend fun findByCategoryId(categoryId: CategoryId): CategoryQuickSummaryResult?
    suspend fun removeAll()
}

data class CategoryQuickSummaryResult(
    val categoryId: CategoryId,
    val numberOfExpenses: Long,
) {
    fun incWithOne(): CategoryQuickSummaryResult =
        this.copy(
            numberOfExpenses = numberOfExpenses + 1,
        )

    fun decWithOne(): CategoryQuickSummaryResult =
        this.copy(
            numberOfExpenses = numberOfExpenses - 1,
        )
}
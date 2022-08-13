package com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId

interface CategoriesQuickSummaryRepository {
    fun saveQuickSummaryResult(categoryQuickSummaryResult: CategoryQuickSummaryResult): CategoryQuickSummaryResult
    fun getQuickSummaries(): List<CategoryQuickSummaryResult>
    fun remove(categoryId: CategoryId)
    fun findByCategoryId(categoryId: CategoryId): CategoryQuickSummaryResult?
    fun removeAll()
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
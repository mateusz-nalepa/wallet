package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

interface CategoriesQuickSummaryRepository {
    fun saveQuickSummary(quickSummary: QuickSummary): QuickSummary
    fun getQuickSummaries(): List<QuickSummary>
    fun remove(categoryId: CategoryId)
    fun findByCategoryId(categoryId: CategoryId): QuickSummary?
}

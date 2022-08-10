package com.mateuszcholyn.wallet.newcode.app.backend.core.category

interface CategoryRepositoryV2 {
    fun save(category: CategoryV2): CategoryV2
    fun getAllCategories(): List<CategoryV2>
    fun getById(categoryId: CategoryId): CategoryV2?
    fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    )
}

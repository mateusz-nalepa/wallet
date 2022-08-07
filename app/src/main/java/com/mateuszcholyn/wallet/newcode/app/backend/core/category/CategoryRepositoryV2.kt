package com.mateuszcholyn.wallet.newcode.app.backend.core.category

interface CategoryRepositoryV2 {
    fun save(category: Category): Category
    fun getAllCategories(): List<Category>
    fun getById(categoryId: CategoryId): Category?
    fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    )
}

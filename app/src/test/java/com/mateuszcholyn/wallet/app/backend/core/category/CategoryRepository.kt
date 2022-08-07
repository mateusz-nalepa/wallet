package com.mateuszcholyn.wallet.app.backend.core.category

interface CategoryRepository {
    fun add(category: Category): Category
    fun getAllCategories(): List<Category>
    fun getById(categoryId: CategoryId): Category?
    fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    )
}

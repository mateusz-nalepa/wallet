package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.Category

interface CategoryRepository {
    suspend fun create(category: Category): Category
    suspend fun update(category: Category): Category
    suspend fun getAllCategories(): List<Category>
    suspend fun getById(categoryId: CategoryId): Category?
    suspend fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    )

    suspend fun removeAllCategories()
}

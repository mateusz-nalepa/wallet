package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2

interface CategoryRepositoryV2 {
    suspend fun create(category: CategoryV2): CategoryV2
    suspend fun update(category: CategoryV2): CategoryV2
    suspend fun getAllCategories(): List<CategoryV2>
    suspend fun getById(categoryId: CategoryId): CategoryV2?
    suspend fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    )

    suspend fun removeAllCategories()
}

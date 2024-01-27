package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2

interface CategoryRepositoryV2 {
    fun save(category: CategoryV2): CategoryV2
    fun getAllCategories(): List<CategoryV2>
    fun getById(categoryId: CategoryId): CategoryV2?
    fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    )

    fun removeAllCategories()
}

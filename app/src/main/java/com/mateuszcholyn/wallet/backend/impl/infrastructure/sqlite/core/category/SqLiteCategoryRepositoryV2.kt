package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2

class SqLiteCategoryRepositoryV2(
    private val categoryV2Dao: CategoryV2Dao,
) : CategoryRepositoryV2 {
    override fun create(category: CategoryV2): CategoryV2 =
        category
            .toEntity()
            .also { categoryV2Dao.create(it) }
            .toDomain()

    override fun update(category: CategoryV2): CategoryV2 =
        category
            .toEntity()
            .also { categoryV2Dao.update(it) }
            .toDomain()

    override fun getAllCategories(): List<CategoryV2> =
        categoryV2Dao
            .getAll()
            .map { it.toDomain() }

    override fun getById(categoryId: CategoryId): CategoryV2? =
        categoryV2Dao
            .getByCategoryId(categoryId.id)
            ?.toDomain()

    override fun remove(categoryId: CategoryId, onExpensesExistAction: (CategoryId) -> Unit) {
        try {
            categoryV2Dao.remove(categoryId.id)
        } catch (t: Throwable) {
            onExpensesExistAction.invoke(categoryId)
        }
    }

    override fun removeAllCategories() {
        categoryV2Dao.removeAll()
    }
}

private fun CategoryV2.toEntity(): CategoryEntityV2 =
    CategoryEntityV2(
        categoryId = id.id,
        name = name,
    )

private fun CategoryEntityV2.toDomain(): CategoryV2 =
    CategoryV2(
        id = CategoryId(categoryId),
        name = name,
    )

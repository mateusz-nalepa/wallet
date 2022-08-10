package com.mateuszcholyn.wallet.newcode.app.backend.core.category

import com.mateuszcholyn.wallet.config.newDatabase.CategoryEntityV2
import com.mateuszcholyn.wallet.config.newDatabase.CategoryV2Dao

class SqLiteCategoryRepositoryV2(
    private val categoryV2Dao: CategoryV2Dao,
) : CategoryRepositoryV2 {
    override fun save(category: CategoryV2): CategoryV2 =
        category
            .toEntity()
            .also { categoryV2Dao.save(it) }
            .toDomain()

    override fun getAllCategories(): List<CategoryV2> =
        categoryV2Dao
            .getAll()
            .map { it.toDomain() }

    override fun getById(categoryId: CategoryId): CategoryV2? =
        categoryV2Dao.getByCategoryId(categoryId.id)
            ?.toDomain()

    override fun remove(categoryId: CategoryId, onExpensesExistAction: (CategoryId) -> Unit) {
        categoryV2Dao.remove(categoryId.id)
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

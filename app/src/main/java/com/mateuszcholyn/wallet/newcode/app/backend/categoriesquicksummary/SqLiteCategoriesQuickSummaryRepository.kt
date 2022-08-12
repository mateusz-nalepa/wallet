package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.config.newDatabase.CategoriesQuickSummaryDao
import com.mateuszcholyn.wallet.config.newDatabase.CategoriesQuickSummaryEntity
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

class SqLiteCategoriesQuickSummaryRepository(
    private val categoriesQuickSummaryDao: CategoriesQuickSummaryDao
) : CategoriesQuickSummaryRepository {

    override fun saveQuickSummaryResult(
        categoryQuickSummaryResult: CategoryQuickSummaryResult,
    ): CategoryQuickSummaryResult =
        categoryQuickSummaryResult
            .toEntity()
            .also { categoriesQuickSummaryDao.save(it) }
            .toDomain()

    override fun getQuickSummaries(): List<CategoryQuickSummaryResult> =
        categoriesQuickSummaryDao
            .getAll()
            .map { it.toDomain() }

    override fun remove(categoryId: CategoryId) {
        categoriesQuickSummaryDao.remove(categoryId.id)
    }

    override fun findByCategoryId(categoryId: CategoryId): CategoryQuickSummaryResult? =
        categoriesQuickSummaryDao
            .findByCategoryId(categoryId.id)
            ?.toDomain()

    override fun removeAll() {
        categoriesQuickSummaryDao.removeAll()
    }
}

private fun CategoryQuickSummaryResult.toEntity(): CategoriesQuickSummaryEntity =
    CategoriesQuickSummaryEntity(
        categoryId = categoryId.id,
        numberOfExpenses = numberOfExpenses,
    )

private fun CategoriesQuickSummaryEntity.toDomain(): CategoryQuickSummaryResult =
    CategoryQuickSummaryResult(
        categoryId = CategoryId(categoryId),
        numberOfExpenses = numberOfExpenses,
    )

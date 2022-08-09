package com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary

import com.mateuszcholyn.wallet.config.newDatabase.CategoriesQuickSummaryDao
import com.mateuszcholyn.wallet.config.newDatabase.CategoriesQuickSummaryEntity
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

class SqLiteCategoriesQuickSummaryRepository(
    private val categoriesQuickSummaryDao: CategoriesQuickSummaryDao
) : CategoriesQuickSummaryRepository {

    override fun saveQuickSummary(quickSummary: QuickSummary): QuickSummary =
        quickSummary
            .also { categoriesQuickSummaryDao.save(it.toEntity()) }

    override fun getQuickSummaries(): List<QuickSummary> =
        categoriesQuickSummaryDao
            .getAll()
            .map { it.toDomain() }

    override fun remove(categoryId: CategoryId) {
        categoriesQuickSummaryDao.remove(categoryId.id)
    }

    override fun findByCategoryId(categoryId: CategoryId): QuickSummary? =
        categoriesQuickSummaryDao
            .findByCategoryId(categoryId.id)
            ?.toDomain()
}

private fun QuickSummary.toEntity(): CategoriesQuickSummaryEntity =
    CategoriesQuickSummaryEntity(
        categoryId = categoryId.id,
        numberOfExpenses = numberOfExpenses,
    )

private fun CategoriesQuickSummaryEntity.toDomain(): QuickSummary =
    QuickSummary(
        categoryId = CategoryId(categoryId),
        numberOfExpenses = numberOfExpenses,
    )

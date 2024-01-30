package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoryQuickSummaryResult
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

class SqLiteCategoriesQuickSummaryRepository(
    private val categoriesQuickSummaryDao: CategoriesQuickSummaryDao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoriesQuickSummaryRepository {

    override suspend fun saveQuickSummaryResult(
        categoryQuickSummaryResult: CategoryQuickSummaryResult,
    ): CategoryQuickSummaryResult =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryQuickSummaryResult
                .toEntity()
                .also { categoriesQuickSummaryDao.save(it) }
                .toDomain()
        }

    override suspend fun getQuickSummaries(): List<CategoryQuickSummaryResult> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoriesQuickSummaryDao
                .getAll()
                .map { it.toDomain() }
        }

    override suspend fun remove(categoryId: CategoryId) {
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoriesQuickSummaryDao.remove(categoryId.id)
        }
    }

    override suspend fun findByCategoryId(categoryId: CategoryId): CategoryQuickSummaryResult? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoriesQuickSummaryDao
                .findByCategoryId(categoryId.id)
                ?.toDomain()
        }

    override suspend fun removeAll() {
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoriesQuickSummaryDao.removeAll()
        }
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

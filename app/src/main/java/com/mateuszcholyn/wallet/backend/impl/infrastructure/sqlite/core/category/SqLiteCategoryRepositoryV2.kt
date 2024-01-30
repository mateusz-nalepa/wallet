package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqLiteCategoryRepositoryV2(
    private val categoryV2Dao: CategoryV2Dao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepositoryV2 {
    override suspend fun create(category: CategoryV2): CategoryV2 =
        withContext(Dispatchers.IO) {
            category
                .toEntity()
                .also { categoryV2Dao.create(it) }
                .toDomain()
        }

    override suspend fun update(category: CategoryV2): CategoryV2 =
        withContext(dispatcherProvider.provideIODispatcher()) {
            category
                .toEntity()
                .also { categoryV2Dao.update(it) }
                .toDomain()
        }

    override suspend fun getAllCategories(): List<CategoryV2> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryV2Dao
                .getAll()
                .map { it.toDomain() }
        }

    override suspend fun getById(categoryId: CategoryId): CategoryV2? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryV2Dao
                .getByCategoryId(categoryId.id)
                ?.toDomain()
        }

    override suspend fun remove(categoryId: CategoryId, onExpensesExistAction: (CategoryId) -> Unit) {
        withContext(dispatcherProvider.provideIODispatcher()) {
            try {
                categoryV2Dao.remove(categoryId.id)
            } catch (t: Throwable) {
                onExpensesExistAction.invoke(categoryId)
            }
        }
    }

    override suspend fun removeAllCategories() {
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryV2Dao.removeAll()
        }
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

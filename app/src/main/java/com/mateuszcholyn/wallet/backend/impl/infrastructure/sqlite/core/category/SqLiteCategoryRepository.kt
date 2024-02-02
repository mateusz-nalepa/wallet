package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SqLiteCategoryRepository(
    private val categoryDao: CategoryDao,
    private val dispatcherProvider: DispatcherProvider,
) : CategoryRepository {
    override suspend fun create(category: Category): Category =
        withContext(Dispatchers.IO) {
            category
                .toEntity()
                .also { categoryDao.create(it) }
                .toDomain()
        }

    override suspend fun update(category: Category): Category =
        withContext(dispatcherProvider.provideIODispatcher()) {
            category
                .toEntity()
                .also { categoryDao.update(it) }
                .toDomain()
        }

    override suspend fun getAllCategories(): List<Category> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryDao
                .getAll()
                .map { it.toDomain() }
        }

    override suspend fun getById(categoryId: CategoryId): Category? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryDao
                .getByCategoryId(categoryId.id)
                ?.toDomain()
        }

    override suspend fun remove(categoryId: CategoryId, onExpensesExistAction: (CategoryId) -> Unit) {
        withContext(dispatcherProvider.provideIODispatcher()) {
            try {
                categoryDao.remove(categoryId.id)
            } catch (t: Throwable) {
                onExpensesExistAction.invoke(categoryId)
            }
        }
    }

    override suspend fun removeAllCategories() {
        withContext(dispatcherProvider.provideIODispatcher()) {
            categoryDao.removeAll()
        }
    }
}

private fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        categoryId = id.id,
        name = name,
    )

private fun CategoryEntity.toDomain(): Category =
    Category(
        id = CategoryId(categoryId),
        name = name,
    )

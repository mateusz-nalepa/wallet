package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
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
                .toEntity(category.parentCategory)
                .also { categoryDao.create(it) }
                .toDomain(category.parentCategory?.toEntity(null))
        }

    override suspend fun update(category: Category): Category =
        withContext(dispatcherProvider.provideIODispatcher()) {
            category
                .toEntity(category.parentCategory)
                .also { categoryDao.update(it) }
                .toDomain(category.parentCategory?.toEntity(null))
        }

    override suspend fun getAllCategories(): List<Category> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            val categories = categoryDao.getAll()

            categories
                .map { rawCategory ->
                    val parentCategory =
                        if (rawCategory.parentCategoryId == null) {
                            null
                        } else {
                            categories.find { it.categoryId == it.parentCategoryId }
                        }

                    rawCategory.toDomain(parentCategory)
                }
        }

    override suspend fun getById(categoryId: CategoryId): Category? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            val rawCategory = categoryDao.getByCategoryId(categoryId.id)
            val parentCategory =
                if (rawCategory?.parentCategoryId == null) {
                    null
                } else {
                    categoryDao.getByCategoryId(rawCategory.parentCategoryId)
                }


            rawCategory?.toDomain(parentCategory) // TODO popraw to XD
        }

    override suspend fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit
    ) {
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

private fun Category.toEntity(parentCategory: Category?): CategoryEntity =
    CategoryEntity(
        categoryId = id.id,
        name = name,
        parentCategoryId = parentCategory?.id?.id,
    )

private fun CategoryEntity.toDomain(parentCategory: CategoryEntity?): Category =
    Category(
        id = CategoryId(categoryId),
        name = name,
        parentCategory = parentCategory?.toDomain(null),
    )

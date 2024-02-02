package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.Category

class CategoryRepositoryFacade(
    private val categoryRepository: CategoryRepository,
) {
    suspend fun create(category: Category): Category =
        categoryRepository.create(category)

    suspend fun update(category: Category): Category =
        categoryRepository.update(category)

    suspend fun getAllCategories(): List<Category> =
        categoryRepository.getAllCategories()

    suspend fun getById(categoryId: CategoryId): Category? =
        categoryRepository.getById(categoryId)

    suspend fun getByIdOrThrow(categoryId: CategoryId): Category =
        getById(categoryId)
            ?: throw CategoryNotFoundException(categoryId)

    suspend fun remove(categoryId: CategoryId) {
        categoryRepository
            .remove(
                categoryId = categoryId,
                onExpensesExistAction = {
                    throw CategoryHasExpensesException(it)
                }
            )
    }

    suspend fun removeAll() {
        categoryRepository
            .removeAllCategories()
    }

}

class CategoryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")

class CategoryHasExpensesException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} has expenses and cannot be removed")

package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2

class CategoryRepositoryFacade(
    private val categoryRepositoryV2: CategoryRepositoryV2,
) {
    suspend fun create(category: CategoryV2): CategoryV2 =
        categoryRepositoryV2.create(category)

    suspend fun update(category: CategoryV2): CategoryV2 =
        categoryRepositoryV2.update(category)

    suspend fun getAllCategories(): List<CategoryV2> =
        categoryRepositoryV2.getAllCategories()

    suspend fun getById(categoryId: CategoryId): CategoryV2? =
        categoryRepositoryV2.getById(categoryId)

    suspend fun getByIdOrThrow(categoryId: CategoryId): CategoryV2 =
        getById(categoryId)
            ?: throw CategoryNotFoundException(categoryId)

    suspend fun remove(categoryId: CategoryId) {
        categoryRepositoryV2
            .remove(
                categoryId = categoryId,
                onExpensesExistAction = {
                    throw CategoryHasExpensesException(it)
                }
            )
    }

    suspend fun removeAll() {
        categoryRepositoryV2
            .removeAllCategories()
    }

}

class CategoryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")

class CategoryHasExpensesException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} has expenses and cannot be removed")

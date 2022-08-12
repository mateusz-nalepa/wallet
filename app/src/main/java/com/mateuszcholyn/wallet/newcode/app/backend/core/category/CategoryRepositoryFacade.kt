package com.mateuszcholyn.wallet.newcode.app.backend.core.category

class CategoryRepositoryFacade(
    private val categoryRepositoryV2: CategoryRepositoryV2,
) {
    fun save(category: CategoryV2): CategoryV2 =
        categoryRepositoryV2.save(category)

    fun getAllCategories(): List<CategoryV2> =
        categoryRepositoryV2.getAllCategories()

    fun getByIdOrThrow(categoryId: CategoryId): CategoryV2 =
        categoryRepositoryV2.getById(categoryId)
            ?: throw CategoryNotFoundException(categoryId)

    fun remove(categoryId: CategoryId) {
        categoryRepositoryV2
            .remove(
                categoryId = categoryId,
                onExpensesExistAction = {
                    throw CategoryHasExpensesException(it)
                }
            )
    }

    fun removeAll() {
        categoryRepositoryV2
            .removeAllCategories()
    }

}

class CategoryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")

class CategoryHasExpensesException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} has expenses and cannot be removed")

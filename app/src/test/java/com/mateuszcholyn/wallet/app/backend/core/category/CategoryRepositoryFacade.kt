package com.mateuszcholyn.wallet.app.backend.core.category

class CategoryRepositoryFacade(
    private val categoryRepository: CategoryRepository,
) {
    fun save(category: Category): Category =
        categoryRepository.save(category)

    fun getAllCategories(): List<Category> =
        categoryRepository.getAllCategories()

    fun getByIdOrThrow(categoryId: CategoryId): Category =
        categoryRepository.getById(categoryId)
            ?: throw CategoryNotFoundException(categoryId)

    fun remove(categoryId: CategoryId) {
        categoryRepository
            .remove(
                categoryId = categoryId,
                onExpensesExistAction = {
                    throw CategoryHasExpensesException(it)
                }
            )
    }

}

class CategoryNotFoundException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")

class CategoryHasExpensesException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} has expenses and cannot be removed")

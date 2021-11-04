package com.mateuszcholyn.wallet.domain.category

class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getByName(category: String): Category {
        return categoryRepository.getCategoryByName(category)
    }

    fun add(category: Category): Category {
        return categoryRepository.add(category)
    }

    fun getAllNamesOnly(): List<String> =
        getAll().map { it.name }

    fun getAll(): List<Category> {
        return categoryRepository.getAll()
    }

    fun remove(category: String): Boolean {
        return categoryRepository.remove(category)
    }

}

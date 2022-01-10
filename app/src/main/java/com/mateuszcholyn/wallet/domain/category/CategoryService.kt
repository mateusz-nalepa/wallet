package com.mateuszcholyn.wallet.domain.category

class CategoryService(private val categoryRepository: CategoryRepository) {

    fun getByName(category: String): Category {
        return categoryRepository.getCategoryByName(category)
    }

    fun add(category: Category): Category {
        return categoryRepository.add(category)
    }

    fun getAllNamesOnly(): List<String> =
        getAllOrderByUsageDesc().map { it.name }

    fun getAllOrderByUsageDesc(): List<Category> {
        return categoryRepository.getAllOrderByUsageDesc()
    }

    fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails> {
        return categoryRepository.getAllWithDetailsOrderByUsageDesc()
    }

    fun remove(categoryId: Long): Boolean {
        return categoryRepository.remove(categoryId)
    }

}

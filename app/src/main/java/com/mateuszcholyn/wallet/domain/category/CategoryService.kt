package com.mateuszcholyn.wallet.domain.category

class CategoryService(
        private val categoryRepository: CategoryRepository,
) {

    fun add(category: Category): Category {
        return categoryRepository.add(category)
    }

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

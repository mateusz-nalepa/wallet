package com.mateuszcholyn.wallet.backend.categorycore

import com.mateuszcholyn.wallet.randomUUID
import java.time.Instant

interface CategoryRepository {
    fun add(category: Category): Category
    fun getAll(): List<Category>
    fun getById(categoryId: CategoryId): Category?
}

class InMemoryCategoryRepository : CategoryRepository {
    private val storage: MutableMap<CategoryId, Category> = mutableMapOf()

    override fun add(category: Category): Category {
        storage[category.id] = category
        return category
    }

    override fun getAll(): List<Category> =
        storage.values.toList()

    override fun getById(categoryId: CategoryId): Category? =
        getAll()
            .find { it.id == categoryId }
}

class CategoryCoreServiceIMPL(
    private val categoryRepository: CategoryRepository,
) : CategoryCoreServiceAPI {
    override fun add(createCategoryParameters: CreateCategoryParameters): Category =
        createCategoryParameters
            .toNewCategory()
            .let { categoryRepository.add(it) }

    override fun getAll(): List<Category> =
        categoryRepository.getAll()

    private fun CreateCategoryParameters.toNewCategory(): Category =
        Category(
            id = CategoryId(randomUUID()),
            name = name,
            createdAt = Instant.now(),
        )
}

package com.mateuszcholyn.wallet.infrastructure.category

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryRepository

class SqLiteCategoryRepository(
    private val categoryDao: CategoryDao,
) : CategoryRepository {
    override fun getAll(): List<Category> {
        return categoryDao
            .getAll()
            .map { it.toDomain() }
    }

    override fun getCategoryByName(name: String): Category {
        return categoryDao.getCategoryByName(name)
            .let { it.toDomain() }
    }

    override fun remove(name: String): Boolean {
        return categoryDao.remove(name) == 1
    }

    override fun add(category: Category): Category {
        return category.toEntity()
            .let { categoryDao.add(it) }
            .let { category.copy(id = it) }
    }

}

fun Category.toEntity(): CategoryEntity =
    CategoryEntity(
        name = name
    )

fun CategoryEntity.toDomain(): Category =
    Category(
        id = categoryId!!,
        name = name!!
    )
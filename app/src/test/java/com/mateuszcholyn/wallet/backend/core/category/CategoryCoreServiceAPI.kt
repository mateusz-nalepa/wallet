package com.mateuszcholyn.wallet.backend.core.category

import java.time.Instant

interface CategoryCoreServiceAPI {
    fun add(createCategoryParameters: CreateCategoryParameters): Category
    fun getAll(): List<Category>
    fun remove(categoryId: CategoryId)
}

data class CreateCategoryParameters(
    val name: String,
)

data class CategoryId(
    val id: String,
)

data class Category(
    val id: CategoryId,
    val name: String,
    val createdAt: Instant,
)
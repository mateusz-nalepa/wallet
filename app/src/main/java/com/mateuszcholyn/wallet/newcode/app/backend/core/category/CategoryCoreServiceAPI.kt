package com.mateuszcholyn.wallet.newcode.app.backend.core.category

interface CategoryCoreServiceAPI {
    fun add(createCategoryParameters: CreateCategoryParameters): Category
    fun getAll(): List<Category>
    fun remove(categoryId: CategoryId)
    fun update(updateCategoryParameters: Category): Category
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
)
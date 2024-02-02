package com.mateuszcholyn.wallet.backend.api.core.category

interface CategoryCoreServiceAPI {
    suspend fun add(createCategoryParameters: CreateCategoryParameters): Category
    suspend fun getAll(): List<Category>
    suspend fun getByIdOrThrow(categoryId: CategoryId): Category
    suspend fun getById(categoryId: CategoryId): Category?
    suspend fun remove(categoryId: CategoryId)
    suspend fun update(updateCategoryParameters: Category): Category
    suspend fun removeAll()
}

data class CreateCategoryParameters(
    val categoryId: CategoryId? = null,
    val name: String,
)

data class CategoryId(
    val id: String,
)

data class Category(
    val id: CategoryId,
    val name: String,
)

fun List<Category>.findOrThrow(categoryId: CategoryId): Category =
    this
        .find { it.id == categoryId }
        ?: throw IllegalStateException("Should not happen :D")
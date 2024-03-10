package com.mateuszcholyn.wallet.backend.api.core.category

interface CategoryCoreServiceAPI {
    suspend fun add(createCategoryParameters: CreateCategoryParameters): Category
    suspend fun getAll(): List<Category>
    suspend fun getAllGrouped(): List<MainCategory>
    suspend fun getByIdOrThrow(categoryId: CategoryId): Category
    suspend fun getById(categoryId: CategoryId): Category?
    suspend fun remove(categoryId: CategoryId)
    suspend fun update(updateCategoryParameters: Category): Category
    suspend fun removeAll()
}

data class CreateCategoryParameters(
    val categoryId: CategoryId? = null,
    val name: String,
    // TODO: remove tego null? XD
    val parentCategory: Category? = null,
)

data class CategoryId(
    val id: String,
)

interface AbstractCategory {
    val id: CategoryId
    val name: String
}

class MainCategory(
    override val id: CategoryId,
    override val name: String,
    val subCategories: List<SubCategory>,
) : AbstractCategory

class SubCategory(
    override val id: CategoryId,
    override val name: String,
) : AbstractCategory

data class Category(
    val id: CategoryId,
    val name: String,
    // TODO: usun tego defaulta stąd XD
    val parentCategory: Category? = null,
)

fun List<Category>.findOrThrow(categoryId: CategoryId): Category =
    this
        .find { it.id == categoryId }
        ?: throw IllegalStateException("Should not happen :D")
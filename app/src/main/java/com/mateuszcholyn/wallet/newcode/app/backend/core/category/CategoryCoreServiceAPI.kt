package com.mateuszcholyn.wallet.newcode.app.backend.core.category

interface CategoryCoreServiceAPI {
    fun add(createCategoryParameters: CreateCategoryParameters): CategoryV2
    fun getAll(): List<CategoryV2>
    fun getByIdOrThrow(categoryId: CategoryId): CategoryV2
    fun remove(categoryId: CategoryId)
    fun update(updateCategoryParameters: CategoryV2): CategoryV2
    fun removeAll()
}

data class CreateCategoryParameters(
    val name: String,
)

data class CategoryId(
    val id: String,
)

data class CategoryV2(
    val id: CategoryId,
    val name: String,
)

fun List<CategoryV2>.findOrThrow(categoryId: CategoryId): CategoryV2 =
    this
        .find { it.id == categoryId }
        ?: throw IllegalStateException("Should not happen :D")
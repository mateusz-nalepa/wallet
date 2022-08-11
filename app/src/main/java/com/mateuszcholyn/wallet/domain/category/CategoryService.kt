package com.mateuszcholyn.wallet.domain.category

class CategoryService(
    private val categoryRepository: CategoryRepository,
) {

    fun add(category: Category): ExistingCategory {
        return categoryRepository.add(category)
    }

    fun updateCategory(category: ExistingCategory): ExistingCategory {
        return categoryRepository.update(category)
    }

    fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails> {
        return categoryRepository
            .getAllCategoriesWithExpenses()
            .orderByCategoryUsageDesc()
    }

    fun getAll(): List<ExistingCategory> {
        return categoryRepository
            .getAll()
    }

    fun remove(categoryId: Long): Boolean {
        return categoryRepository.remove(categoryId)
    }

    fun removeAll(): Boolean {
        return categoryRepository.removeAll()
    }

}

fun List<CategoryWithExpenses>.orderByCategoryUsageDesc(): List<CategoryDetails> =
    TODO("remove me XDD")
    //    this
//        .groupBy { it.category.name }
//        .mapValues { categoryNameWithListOfExpenses ->
//            CategoryIdWithNumberOfExpenses(
//                categoryId = categoryNameWithListOfExpenses.value.firstOrNull()?.category?.id
//                    ?: throw IllegalStateException("Id should not be null"),
//                numberOfExpenses = categoryNameWithListOfExpenses.value.first().expenses.size.toLong(),
//            )
//        }
//        .toList()
//        .map {
//            CategoryDetails(
//                id = it.second.categoryId,
//                name = it.first,
//                numberOfExpenses = it.second.numberOfExpenses,
//            )
//        }
//        .sortedByDescending { it.numberOfExpenses }

data class CategoryIdWithNumberOfExpenses(
    val categoryId: Long,
    val numberOfExpenses: Long,
)
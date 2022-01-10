package com.mateuszcholyn.wallet.infrastructure.category

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.CategoryWithExpense
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseWithCategory

class SqLiteCategoryRepository(
        private val categoryDao: CategoryDao,
) : CategoryRepository {

    private val categoryQueriesHelper = CategoryQueriesHelper()

    override fun getAllOrderByUsageDesc(): List<Category> {
        return categoryDao
                .getAllOrderByUsageDesc(categoryQueriesHelper.getAllOrderByUsageDesc())
                .map { it.toDomain() }
    }

    override fun getAllWithDetailsOrderByUsageDesc(): List<CategoryDetails> {
        return categoryDao
                .getAllDataFromDb()
                .toCategoriesDetails()
    }

    override fun getCategoryByName(name: String): Category {
        return categoryDao.getCategoryByName(name)
                .let { it.toDomain() }
    }

    override fun remove(categoryId: Long): Boolean {
        return categoryDao.remove(categoryId) == 1
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

fun ExpenseWithCategory.toCategoryDetails(): CategoryDetails {
    return CategoryDetails(
            id = 5,
            name = "5",
            numberOfExpenses = 4,
    )
}

fun List<CategoryWithExpense>.toCategoriesDetails(): List<CategoryDetails> =
        groupBy { it.categoryEntity.name }
                .mapValues {
                    CategoryIdWithNumberOfExpenses(
                            categoryId = it.value.first().categoryEntity.categoryId!!,
                            numberOfExpenses = it.value.filter { itt -> itt.expenseEntity != null }.size.toLong(),
                    )
                }
                .mapKeys { it.key!! }
                .toList()
                .map {
                    CategoryDetails(
                            id = it.second.categoryId,
                            name = it.first,
                            numberOfExpenses = it.second.numberOfExpenses,
                    )
                }
                .sortedByDescending { it.numberOfExpenses }


data class CategoryIdWithNumberOfExpenses(
        val categoryId: Long,
        val numberOfExpenses: Long,
)
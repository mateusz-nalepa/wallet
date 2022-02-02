package com.mateuszcholyn.wallet.infrastructure.category

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.CategoryWithExpense

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

    override fun remove(categoryId: Long): Boolean {
        return categoryDao.remove(categoryId) == 1
    }

    override fun add(category: Category): Category {
        return category
                .toEntityAdd()
                .let { categoryDao.add(it) }
                .let { category.copy(id = it) }
    }

    override fun update(category: Category): Category {
        category
                .toEntityUpdate()
                .let { categoryDao.update(it) }

        return category
    }

}

fun Category.toEntityUpdate(): CategoryEntity =
        CategoryEntity(
                categoryId = id,
                name = name
        )

fun Category.toEntityAdd(): CategoryEntity =
        CategoryEntity(
                categoryId = null,
                name = name
        )

fun CategoryEntity.toDomain(): Category =
        Category(
                id = categoryId!!,
                name = name!!
        )

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
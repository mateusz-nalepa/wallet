package com.mateuszcholyn.wallet.infrastructure.category

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryWithExpenses
import com.mateuszcholyn.wallet.domain.category.ExistingCategory
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.infrastructure.expense.toDomain

class SqLiteCategoryRepository(
    private val categoryDao: CategoryDao,
) : CategoryRepository {

    override fun remove(categoryId: Long): Boolean {
        return categoryDao.remove(categoryId) == 1
    }

    override fun removeAll(): Boolean {
        categoryDao.removeAll()
        return true
    }

    override fun getAll(): List<ExistingCategory> {
        return categoryDao.getAll()
            .map { it.toDomain() }
    }

    override fun add(category: Category): ExistingCategory {
        return category
            .toEntityAdd()
            .let { categoryDao.add(it) }
            .let { category.copy(id = it) }
            .toDomain()
    }

    override fun update(category: ExistingCategory): ExistingCategory {
        category
            .toEntityUpdate()
            .let { categoryDao.update(it) }

        return category
    }

    override fun getAllCategoriesWithExpenses(): List<CategoryWithExpenses> {
        return categoryDao.getAllCategoriesWithExpenses()
            .groupBy { it.categoryEntity }
            .map {
                CategoryWithExpenses(
                    category = it.key.toDomain(),
                    expenses = it.value.toExpenses(it.key)
                )
            }
    }

}

fun List<CategoryWithExpense>.toExpenses(categoryEntity: CategoryEntity): List<Expense> =
    this
        .filter { it.expenseEntity != null }
        .map { it.expenseEntity!!.toDomain(categoryEntity) }

fun ExistingCategory.toEntityUpdate(): CategoryEntity =
    CategoryEntity(
        categoryId = id,
        name = name
    )

fun Category.toEntityAdd(): CategoryEntity =
    CategoryEntity(
        categoryId = null,
        name = name
    )

fun CategoryEntity.toDomain(): ExistingCategory =
    ExistingCategory(
        id = categoryId!!,
        name = name!!
    )


fun Category.toDomain(): ExistingCategory =
    ExistingCategory(
        id = id!!,
        name = name
    )

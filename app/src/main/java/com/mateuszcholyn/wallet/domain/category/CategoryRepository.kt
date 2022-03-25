package com.mateuszcholyn.wallet.domain.category

import com.mateuszcholyn.wallet.domain.expense.Expense

interface CategoryRepository {

    fun remove(categoryId: Long): Boolean
    fun add(category: Category): ExistingCategory
    fun update(category: ExistingCategory): ExistingCategory
    fun getAllCategoriesWithExpenses(): List<CategoryWithExpenses>

}

data class CategoryWithExpenses(
        val category: ExistingCategory,
        val expenses: List<Expense>,
)
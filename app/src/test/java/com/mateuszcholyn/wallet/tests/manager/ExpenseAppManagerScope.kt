package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import com.mateuszcholyn.wallet.randomCategoryName

class ExpenseAppManagerScope {

    val categoriesScope = mutableListOf<CategoryScope>()

    fun category(scope: CategoryScope.() -> Unit): CategoryScope {
        val categoryScope = CategoryScope().apply(scope)
        categoriesScope.add(categoryScope)
        return categoryScope
    }

}

class CategoryScope {
    lateinit var categoryId: CategoryId
    var categoryName = randomCategoryName()
}
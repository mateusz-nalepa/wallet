package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.categorycore.Category
import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.validate(
    categoryId: CategoryId,
    validateBlock: SimpleCategoryValidator.() -> Unit,
) {
    val category = this.expenseAppDependencies.categoryRepository.getById(categoryId)
    requireNotNull(category) { "Category with id $categoryId should exist" }
    category.validate(validateBlock)
}


fun Category.validate(validateBlock: SimpleCategoryValidator.() -> Unit) {
    SimpleCategoryValidator(this).apply(validateBlock)
}

class SimpleCategoryValidator(
    private val category: Category,
) {
    fun nameEqualTo(expectedCategoryName: String) {
        assert(category.name == expectedCategoryName) {
            "Expected category name is: $expectedCategoryName. Actual: ${category.name}"
        }
    }
}



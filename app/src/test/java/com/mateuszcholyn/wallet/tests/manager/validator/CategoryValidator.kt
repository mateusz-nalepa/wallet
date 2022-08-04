package com.mateuszcholyn.wallet.tests.manager.validator

import com.mateuszcholyn.wallet.backend.core.Category
import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.validate(
    categoryId: CategoryId,
    validateBlock: FullCategoryValidator.() -> Unit,
) {
    val category = this.expenseAppDependencies.categoryRepository.getById(categoryId)
    requireNotNull(category) { "Category with id $categoryId should exist" }

    FullCategoryValidator(
        category = category,
    ).apply(validateBlock)
}


class FullCategoryValidator(
    private val category: Category,
) {
    fun nameEqualTo(expectedCategoryName: String) {
        SimpleCategoryValidator(category).nameEqualTo(expectedCategoryName)
    }
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

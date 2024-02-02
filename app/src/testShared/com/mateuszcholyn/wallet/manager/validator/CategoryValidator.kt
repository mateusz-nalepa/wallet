package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.validate(
    categoryId: CategoryId,
    validateBlock: FullCategoryValidator.() -> Unit,
) {
    runBlocking {
        val category = expenseAppDependencies.categoryRepository.getById(categoryId)
        requireNotNull(category) { "Category with id $categoryId should exist" }

        FullCategoryValidator(
            category = category,
        ).apply(validateBlock)
    }
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

    fun isSameAsCategoryFromBackup(
        backupCategory: BackupWalletV1.BackupCategoryV1,
    ) {
        nameEqualTo(backupCategory.name)
        idEqualTo(CategoryId(backupCategory.id))
    }

    fun isSameAsExistingCategory(
        categoryScope: CategoryScope,
    ) {
        nameEqualTo(categoryScope.categoryName)
        idEqualTo(categoryScope.categoryId)
    }

    fun nameEqualTo(expectedCategoryName: String) {
        assert(category.name == expectedCategoryName) {
            "Expected category name is: $expectedCategoryName. Actual: ${category.name}"
        }
    }

    fun idEqualTo(expectedCategoryId: CategoryId) {
        assert(category.id == expectedCategoryId) {
            "Expected categoryId is: $expectedCategoryId. Actual: ${category.id}"
        }
    }
}

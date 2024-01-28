package com.mateuszcholyn.wallet.manager.validator

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.CategoryScope
import com.mateuszcholyn.wallet.manager.ExpenseAppManager

fun ExpenseAppManager.validate(
    categoryId: CategoryId,
    validateBlock: FullCategoryValidator.() -> Unit,
) {
    val category = this.expenseAppDependencies.categoryRepositoryV2.getById(categoryId)
    requireNotNull(category) { "Category with id $categoryId should exist" }

    FullCategoryValidator(
        category = category,
    ).apply(validateBlock)
}


class FullCategoryValidator(
    private val category: CategoryV2,
) {
    fun nameEqualTo(expectedCategoryName: String) {
        SimpleCategoryValidator(category).nameEqualTo(expectedCategoryName)
    }
}

fun CategoryV2.validate(validateBlock: SimpleCategoryValidator.() -> Unit) {
    SimpleCategoryValidator(this).apply(validateBlock)
}

class SimpleCategoryValidator(
    private val category: CategoryV2,
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

package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CategoryRemovedStatus

fun ExpenseAppManager.removeCategoryUseCase(
    scope: RemoveCategoryUseCaseScope.() -> Unit,
): CategoryRemovedStatus {

    val categoryId =
        RemoveCategoryUseCaseScope()
            .apply(scope)
            .toRemoveCategoryParameters()

    return this
        .expenseAppUseCases
        .removeCategoryUseCase
        .invoke(categoryId)
}

class RemoveCategoryUseCaseScope {
    var categoryId: CategoryId = randomCategoryId()

    fun toRemoveCategoryParameters(): CategoryId =
        categoryId
}
package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.usecase.CategoryRemovedStatus

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
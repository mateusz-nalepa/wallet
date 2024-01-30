package com.mateuszcholyn.wallet.manager.ext.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CategoryRemovedStatus
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomCategoryId
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.removeCategoryUseCase(
    scope: RemoveCategoryUseCaseScope.() -> Unit,
): CategoryRemovedStatus {

    val categoryId =
        RemoveCategoryUseCaseScope()
            .apply(scope)
            .toRemoveCategoryParameters()

    return runBlocking {
        expenseAppUseCases
            .removeCategoryUseCase
            .invoke(categoryId)
    }
}

class RemoveCategoryUseCaseScope {
    var categoryId: CategoryId = randomCategoryId()

    fun toRemoveCategoryParameters(): CategoryId =
        categoryId
}
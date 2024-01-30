package com.mateuszcholyn.wallet.manager.ext.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomCategoryName
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.updateCategoryUseCase(
    scope: UpdateCategoryUseCaseScope.() -> Unit,
): CategoryV2 {

    val updateCategoryUseCaseParameters =
        UpdateCategoryUseCaseScope()
            .apply(scope)
            .toCategory()

    return runBlocking {
        expenseAppUseCases
            .updateCategoryUseCase
            .invoke(updateCategoryUseCaseParameters)
    }
}

class UpdateCategoryUseCaseScope {
    var existingCategoryId = randomCategoryId()
    var newName = randomCategoryName()

    fun toCategory(): CategoryV2 =
        CategoryV2(
            id = existingCategoryId,
            name = newName,
        )
}
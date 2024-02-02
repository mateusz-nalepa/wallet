package com.mateuszcholyn.wallet.manager.ext.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomCategoryName
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.updateCategoryUseCase(
    scope: UpdateCategoryUseCaseScope.() -> Unit,
): Category {

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

    fun toCategory(): Category =
        Category(
            id = existingCategoryId,
            name = newName,
        )
}
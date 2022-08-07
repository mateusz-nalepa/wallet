package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.Category
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager

fun ExpenseAppManager.updateCategoryUseCase(
    scope: UpdateCategoryUseCaseScope.() -> Unit,
): Category {

    val updateCategoryUseCaseParameters =
        UpdateCategoryUseCaseScope()
            .apply(scope)
            .toCategory()

    return this
        .expenseAppUseCases
        .updateCategoryUseCase
        .invoke(updateCategoryUseCaseParameters)
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
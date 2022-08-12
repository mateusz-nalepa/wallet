package com.mateuszcholyn.wallet.tests.manager.ext.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.randomCategoryId
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName

fun ExpenseAppManager.updateCategoryUseCase(
    scope: UpdateCategoryUseCaseScope.() -> Unit,
): CategoryV2 {

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

    fun toCategory(): CategoryV2 =
        CategoryV2(
            id = existingCategoryId,
            name = newName,
        )
}
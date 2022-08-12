package com.mateuszcholyn.wallet.tests.manager.ext.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName


fun ExpenseAppManager.createCategoryUseCase(
    scope: CreateCategoryUseCaseScope.() -> Unit,
): CategoryV2 {

    val createCategoryParameters =
        CreateCategoryUseCaseScope()
            .apply(scope)
            .toCreateCategoryParameters()

    return this
        .expenseAppUseCases
        .createCategoryUseCase
        .invoke(createCategoryParameters)
}

class CreateCategoryUseCaseScope {
    var name: String = randomCategoryName()

    fun toCreateCategoryParameters(): CreateCategoryParameters =
        CreateCategoryParameters(
            name = name,
        )
}
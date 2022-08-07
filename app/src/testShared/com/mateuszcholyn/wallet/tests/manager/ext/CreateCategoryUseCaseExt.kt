package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.Category
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.tests.manager.randomCategoryName
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager


fun ExpenseAppManager.createCategoryUseCase(
    scope: CreateCategoryUseCaseScope.() -> Unit,
): Category {

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
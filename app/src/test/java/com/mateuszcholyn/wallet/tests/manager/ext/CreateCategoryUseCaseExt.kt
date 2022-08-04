package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.Category
import com.mateuszcholyn.wallet.backend.core.CreateCategoryParameters
import com.mateuszcholyn.wallet.randomCategoryName
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
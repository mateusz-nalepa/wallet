package com.mateuszcholyn.wallet.manager.ext.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.randomCategoryName


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
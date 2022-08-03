package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categorycore.CreateCategoryParameters


class ExpenseAppInitializer(
    val expenseAppManagerScope: ExpenseAppManagerScope,
    val expenseAppDependencies: ExpenseAppDependencies,
    val expenseAppUseCases: ExpenseAppUseCases,
) {
    fun init() {
        addCategories()
    }

    private fun addCategories() {
        expenseAppManagerScope
            .categoriesScope
            .forEach { addCategory(it) }
    }

    private fun addCategory(categoryScope: CategoryScope) {
        val createCategoryParameters =
            CreateCategoryParameters(
                name = categoryScope.categoryName
            )
        val category =
            expenseAppUseCases.createCategoryUseCase.invoke(createCategoryParameters)

        categoryScope.categoryId = category.id
    }
}
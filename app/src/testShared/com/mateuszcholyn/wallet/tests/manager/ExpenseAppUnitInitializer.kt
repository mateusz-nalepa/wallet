package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.AddExpenseParameters


class ExpenseAppUnitInitializer(
    val expenseAppManagerScope: ExpenseAppManagerScope,
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

        addExpenses(categoryScope)
    }

    private fun addExpenses(categoryScope: CategoryScope) {
        categoryScope
            .expensesScope
            .forEach { expenseScope ->
                addExpense(categoryScope.categoryId, expenseScope)
            }
    }

    private fun addExpense(categoryId: CategoryId, expenseScope: ExpenseScope) {
        val addExpenseParameters =
            AddExpenseParameters(
                amount = expenseScope.amount,
                description = expenseScope.description,
                paidAt = expenseScope.paidAt,
                categoryId = categoryId,
            )
        val category =
            expenseAppUseCases.addExpenseUseCase.invoke(addExpenseParameters)

        expenseScope.expenseId = category.id
    }

}
package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import kotlinx.coroutines.runBlocking


class ExpenseAppInitializer(
    val expenseAppManagerScope: ExpenseAppManagerScope,
    val expenseAppUseCases: ExpenseAppUseCases,
) {
    fun init() {
        runBlocking {
            addCategories()
        }
    }

    private suspend fun addCategories() {
        expenseAppManagerScope
            .categoriesScope
            .forEach { addCategory(it, null) }
    }

    private suspend fun addCategory(
        categoryScope: CategoryScope,
        parentCategoryScope: CategoryScope?,
    ) {
        val createCategoryParameters =
            CreateCategoryParameters(
                name = categoryScope.categoryName,
                parentCategory = parentCategoryScope?.let {
                    Category(
                        id = it.categoryId,
                        name = it.categoryName,
                    )
                }
            )
        val category =
            expenseAppUseCases.createCategoryUseCase.invoke(createCategoryParameters)

        categoryScope.categoryId = category.id

        // TODO: to chyba dobrze jest XD
        categoryScope.subCategoriesScope.forEach { addCategory(it, categoryScope) }

        addExpenses(categoryScope)
    }

    private suspend fun addExpenses(
        categoryScope: CategoryScope,
    ) {
        categoryScope
            .expensesScope
            .forEach { expenseScope ->
                addExpense(categoryScope.categoryId, expenseScope)
            }
    }

    private suspend fun addExpense(
        categoryId: CategoryId,
        expenseScope: ExpenseScope,
    ) {
        val addExpenseParameters =
            AddExpenseParameters(
                amount = expenseScope.amount,
                description = expenseScope.description,
                paidAt = expenseScope.paidAt,
                // TODO: HODOR - to na pewno jest coś nie tak XD
                categoryId = categoryId,
            )
        val category =
            expenseAppUseCases.addExpenseUseCase.invoke(addExpenseParameters)

        expenseScope.expenseId = category.expenseId
    }

}
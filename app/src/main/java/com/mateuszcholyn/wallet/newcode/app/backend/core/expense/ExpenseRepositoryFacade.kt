package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

class ExpenseRepositoryFacade(
    private val expenseRepositoryV2: ExpenseRepositoryV2,
) {
    fun save(
        expense: Expense,
    ): Expense =
        expenseRepositoryV2.save(
            expense = expense,
            onNonExistingCategoryAction = { categoryId ->
                throw CategoryWithGivenIdDoesNotExist(categoryId)
            }
        )

    fun getAllExpenses(): List<Expense> =
        expenseRepositoryV2.getAllExpenses()

    fun getByIdOrThrow(expenseId: ExpenseId): Expense =
        expenseRepositoryV2.getById(expenseId)
            ?: throw ExpenseNotFoundException(expenseId)

    fun remove(expenseId: ExpenseId) {
        expenseRepositoryV2.remove(expenseId)
    }


}

class ExpenseNotFoundException(expenseId: ExpenseId) :
    RuntimeException("Expense with id ${expenseId.id} does not exist")

class CategoryWithGivenIdDoesNotExist(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")


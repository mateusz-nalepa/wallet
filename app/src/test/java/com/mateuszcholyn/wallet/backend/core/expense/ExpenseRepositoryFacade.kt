package com.mateuszcholyn.wallet.backend.core.expense

import com.mateuszcholyn.wallet.backend.core.category.CategoryId

class ExpenseRepositoryFacade(
    private val expenseRepository: ExpenseRepository,
) {
    fun save(
        expense: Expense,
    ): Expense =
        expenseRepository.save(
            expense = expense,
            onNonExistingCategoryAction = { categoryId ->
                throw CategoryWithGivenIdDoesNotExist(categoryId)
            }
        )

    fun getAllExpenses(): List<Expense> =
        expenseRepository.getAllExpenses()

    fun getByIdOrThrow(expenseId: ExpenseId): Expense =
        expenseRepository.getById(expenseId)
            ?: throw ExpenseNotFoundException(expenseId)

    fun remove(expenseId: ExpenseId) {
        expenseRepository.remove(expenseId)
    }


}

class ExpenseNotFoundException(expenseId: ExpenseId) :
    RuntimeException("Expense with id ${expenseId.id} does not exist")

class CategoryWithGivenIdDoesNotExist(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")


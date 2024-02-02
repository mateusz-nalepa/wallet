package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense

class ExpenseRepositoryFacade(
    private val expenseRepository: ExpenseRepository,
) {
    suspend fun create(
        expense: Expense,
    ): Expense =
        expenseRepository.create(
            expense = expense,
            onNonExistingCategoryAction = { categoryId ->
                throw CategoryWithGivenIdDoesNotExist(categoryId)
            }
        )

    suspend fun update(
        expense: Expense,
    ): Expense =
        expenseRepository.update(
            expense = expense,
            onNonExistingCategoryAction = { categoryId ->
                throw CategoryWithGivenIdDoesNotExist(categoryId)
            }
        )

    suspend fun getAllExpenses(): List<Expense> =
        expenseRepository.getAllExpenses()

    suspend fun getById(expenseId: ExpenseId): Expense? =
        expenseRepository.getById(expenseId)

    suspend fun getByIdOrThrow(expenseId: ExpenseId): Expense =
        getById(expenseId)
            ?: throw ExpenseNotFoundException(expenseId)

    suspend fun remove(expenseId: ExpenseId) {
        expenseRepository.remove(expenseId)
    }

    suspend fun removeAll() {
        expenseRepository.removeAllExpenses()
    }


}

class ExpenseNotFoundException(expenseId: ExpenseId) :
    RuntimeException("Expense with id ${expenseId.id} does not exist")

class CategoryWithGivenIdDoesNotExist(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")


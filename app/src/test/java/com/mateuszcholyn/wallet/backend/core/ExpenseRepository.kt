package com.mateuszcholyn.wallet.backend.core

interface ExpenseRepository {
    fun save(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): Expense

    fun getAllExpenses(): List<Expense>
    fun getById(expenseId: ExpenseId): Expense?
    fun remove(expenseId: ExpenseId)
}

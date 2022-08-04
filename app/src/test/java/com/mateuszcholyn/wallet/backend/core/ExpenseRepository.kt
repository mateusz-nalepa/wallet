package com.mateuszcholyn.wallet.backend.core

interface ExpenseRepository {
    fun add(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): Expense

    fun getAllExpenses(): List<Expense>
    fun getById(expenseId: ExpenseId): Expense?
    fun remove(expenseId: ExpenseId)
}

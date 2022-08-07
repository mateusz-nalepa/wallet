package com.mateuszcholyn.wallet.app.backend.core.expense

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId

interface ExpenseRepository {
    fun save(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): Expense

    fun getAllExpenses(): List<Expense>
    fun getById(expenseId: ExpenseId): Expense?
    fun remove(expenseId: ExpenseId)
}

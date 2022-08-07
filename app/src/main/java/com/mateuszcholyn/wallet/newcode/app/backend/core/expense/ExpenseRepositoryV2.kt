package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

interface ExpenseRepositoryV2 {
    fun save(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): Expense

    fun getAllExpenses(): List<Expense>
    fun getById(expenseId: ExpenseId): Expense?
    fun remove(expenseId: ExpenseId)
}

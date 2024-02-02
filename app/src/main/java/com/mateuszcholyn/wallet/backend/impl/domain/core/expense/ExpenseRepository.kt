package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense

interface ExpenseRepository {
    suspend fun create(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): Expense

    suspend fun update(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): Expense

    suspend fun getAllExpenses(): List<Expense>
    suspend fun getById(expenseId: ExpenseId): Expense?
    suspend fun remove(expenseId: ExpenseId)
    suspend fun removeAllExpenses()
}

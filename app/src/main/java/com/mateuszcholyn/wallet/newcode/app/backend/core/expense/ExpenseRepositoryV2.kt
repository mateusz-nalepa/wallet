package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

interface ExpenseRepositoryV2 {
    fun save(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): ExpenseV2

    fun getAllExpenses(): List<ExpenseV2>
    fun getById(expenseId: ExpenseId): ExpenseV2?
    fun remove(expenseId: ExpenseId)
}

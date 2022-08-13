package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2

interface ExpenseRepositoryV2 {
    fun save(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): ExpenseV2

    fun getAllExpenses(): List<ExpenseV2>
    fun getById(expenseId: ExpenseId): ExpenseV2?
    fun remove(expenseId: ExpenseId)
    fun removeAllExpenses()
}

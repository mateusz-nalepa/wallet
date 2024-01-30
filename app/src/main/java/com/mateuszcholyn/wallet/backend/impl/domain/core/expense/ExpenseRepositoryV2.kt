package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2

// TODO: pousuwaj wszędzie te `V2` gdzie nie są naprawde potrzebne XDD
interface ExpenseRepositoryV2 {
    suspend fun create(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): ExpenseV2

    suspend fun update(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit = {},
    ): ExpenseV2

    suspend fun getAllExpenses(): List<ExpenseV2>
    suspend fun getById(expenseId: ExpenseId): ExpenseV2?
    suspend fun remove(expenseId: ExpenseId)
    suspend fun removeAllExpenses()
}

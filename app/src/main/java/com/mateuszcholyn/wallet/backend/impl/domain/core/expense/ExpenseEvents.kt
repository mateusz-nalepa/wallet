package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import java.math.BigDecimal
import java.time.Instant

data class ExpenseAddedEvent(
    val expenseId: ExpenseId,
    val categoryId: CategoryPair,
    val amount: BigDecimal,
    val paidAt: Instant,
    val description: String,
)

data class CategoryPair(
    val categoryId: CategoryId,
    val subCategoryId: SubCategoryId?,
)

data class ExpenseUpdatedEvent(
    val expenseId: ExpenseId,
    val oldCategoryId: CategoryPair,
    val newCategoryId: CategoryPair,
    val newAmount: BigDecimal,
    val newPaidAt: Instant,
    val newDescription: String,
)

data class ExpenseRemovedEvent(
    val categoryId: CategoryId,
    val expenseId: ExpenseId,
)

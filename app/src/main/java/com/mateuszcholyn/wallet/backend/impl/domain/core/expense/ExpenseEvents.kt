package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import java.math.BigDecimal
import java.time.Instant

data class ExpenseAddedEvent(
    val expenseId: ExpenseId,
    val categoryId: CategoryId,
    val amount: BigDecimal,
    val paidAt: Instant,
    val description: String,
)

data class ExpenseUpdatedEvent(
    val expenseId: ExpenseId,
    val oldCategoryId: CategoryId,
    val newCategoryId: CategoryId,
    val newAmount: BigDecimal,
    val newPaidAt: Instant,
    val newDescription: String,
)

data class ExpenseRemovedEvent(
    val categoryId: CategoryId,
    val expenseId: ExpenseId,
)

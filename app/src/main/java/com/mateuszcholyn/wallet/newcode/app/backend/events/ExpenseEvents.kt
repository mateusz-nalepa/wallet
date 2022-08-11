package com.mateuszcholyn.wallet.newcode.app.backend.events

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import java.math.BigDecimal
import java.time.LocalDateTime

data class ExpenseAddedEvent(
    val expenseId: ExpenseId,
    val categoryId: CategoryId,
    val amount: BigDecimal,
    val paidAt: LocalDateTime,
    val description: String,
)

data class ExpenseUpdatedEvent(
    val expenseId: ExpenseId,
    val oldCategoryId: CategoryId,
    val newCategoryId: CategoryId,
    val newAmount: BigDecimal,
    val newPaidAt: LocalDateTime,
    val newDescription: String,
)

data class ExpenseRemovedEvent(
    val categoryId: CategoryId,
    val expenseId: ExpenseId,
)

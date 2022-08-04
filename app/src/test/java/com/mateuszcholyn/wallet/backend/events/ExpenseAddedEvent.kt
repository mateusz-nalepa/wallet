package com.mateuszcholyn.wallet.backend.events

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.core.ExpenseId
import java.math.BigDecimal

data class ExpenseAddedEvent(
    val expenseId: ExpenseId,
    val categoryId: CategoryId,
    val amount: BigDecimal,
)

data class ExpenseRemovedEvent(
    val categoryId: CategoryId,
    val expenseId: ExpenseId,
)

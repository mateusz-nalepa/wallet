package com.mateuszcholyn.wallet.backend.expensecore

import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import java.math.BigDecimal
import java.time.Instant

interface ExpenseCoreServiceAPI {
    fun add(createExpenseParameters: CreateExpenseParameters): Expense
    fun getAll(): List<Expense>
}

data class CreateExpenseParameters(
    val amount: BigDecimal,
    var description: String,
    var paidAt: Instant,
    var categoryId: CategoryId
)

data class ExpenseId(
    val id: String,
)

data class Expense(
    val id: ExpenseId,
    val amount: BigDecimal,
    var description: String,
    var paidAt: Instant,
    var categoryId: CategoryId
)
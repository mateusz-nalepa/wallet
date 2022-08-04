package com.mateuszcholyn.wallet.backend.core

import java.math.BigDecimal
import java.time.Instant

interface ExpenseCoreServiceAPI {
    fun add(addExpenseParameters: AddExpenseParameters): Expense
    fun remove(expenseId: ExpenseId)
    fun getAll(): List<Expense>
}

data class AddExpenseParameters(
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
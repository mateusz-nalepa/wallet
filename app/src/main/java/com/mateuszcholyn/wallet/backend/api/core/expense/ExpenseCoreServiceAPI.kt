package com.mateuszcholyn.wallet.backend.api.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import java.math.BigDecimal
import java.time.Instant

interface ExpenseCoreServiceAPI {
    suspend fun add(addExpenseParameters: AddExpenseParameters): Expense
    suspend fun remove(expenseId: ExpenseId)
    suspend fun getAll(): List<Expense>
    suspend fun update(updateExpenseParameters: Expense): Expense
    suspend fun getById(expenseId: ExpenseId): Expense?
    suspend fun getByIdOrThrow(expenseId: ExpenseId): Expense
    suspend fun removeAll()
}

data class AddExpenseParameters(
    val expenseId: ExpenseId? = null,
    val amount: BigDecimal,
    val description: String,
    val paidAt: Instant,
    val categoryId: CategoryId,
)

data class ExpenseId(
    val id: String,
)

data class Expense(
    val expenseId: ExpenseId,
    val amount: BigDecimal,
    val description: String,
    val paidAt: Instant,
    val categoryId: CategoryId,
)

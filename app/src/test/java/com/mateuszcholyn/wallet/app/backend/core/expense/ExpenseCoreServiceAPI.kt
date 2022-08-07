package com.mateuszcholyn.wallet.app.backend.core.expense

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId
import java.math.BigDecimal
import java.time.LocalDateTime

interface ExpenseCoreServiceAPI {
    fun add(addExpenseParameters: AddExpenseParameters): Expense
    fun remove(expenseId: ExpenseId)
    fun getAll(): List<Expense>
    fun update(updateExpenseParameters: Expense): Expense
}

data class AddExpenseParameters(
    val amount: BigDecimal,
    var description: String,
    var paidAt: LocalDateTime,
    var categoryId: CategoryId
)

data class ExpenseId(
    val id: String,
)

data class Expense(
    val id: ExpenseId,
    val amount: BigDecimal,
    var description: String,
    var paidAt: LocalDateTime,
    var categoryId: CategoryId
)
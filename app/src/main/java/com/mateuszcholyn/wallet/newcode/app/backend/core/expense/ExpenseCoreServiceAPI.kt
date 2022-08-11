package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import java.math.BigDecimal
import java.time.LocalDateTime

interface ExpenseCoreServiceAPI {
    fun add(addExpenseParameters: AddExpenseParameters): ExpenseV2
    fun remove(expenseId: ExpenseId)
    fun getAll(): List<ExpenseV2>
    fun update(updateExpenseParameters: ExpenseV2): ExpenseV2
    fun getByIdOrThrow(expenseId: ExpenseId): ExpenseV2WithCategory
}

data class AddExpenseParameters(
    val amount: BigDecimal,
    val description: String,
    val paidAt: LocalDateTime,
    val categoryId: CategoryId
)

data class ExpenseId(
    val id: String,
)

data class ExpenseV2(
    val expenseId: ExpenseId,
    val amount: BigDecimal,
    val description: String,
    val paidAt: LocalDateTime,
    val categoryId: CategoryId
)

data class ExpenseV2WithCategory(
    val expenseId: ExpenseId,
    val amount: BigDecimal,
    val description: String,
    val paidAt: LocalDateTime,
    val categoryId: CategoryId,
    val categoryName: String,
)
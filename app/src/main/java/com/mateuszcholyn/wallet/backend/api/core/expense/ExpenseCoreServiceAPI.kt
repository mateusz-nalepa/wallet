package com.mateuszcholyn.wallet.backend.api.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import java.math.BigDecimal
import java.time.Instant

// TODO: powydzielaj useCasy też po stronie backendu xD
interface ExpenseCoreServiceAPI {
    fun add(addExpenseParameters: AddExpenseParameters): ExpenseV2
    fun remove(expenseId: ExpenseId)
    fun getAll(): List<ExpenseV2>
    fun update(updateExpenseParameters: ExpenseV2): ExpenseV2
    fun getExpenseWithCategoryDetails(expenseId: ExpenseId): ExpenseV2WithCategory
    fun getById(expenseId: ExpenseId): ExpenseV2?
    fun removeAll()
}

data class AddExpenseParameters(
    val expenseId: ExpenseId? = null,
    val amount: BigDecimal,
    val description: String,
    val paidAt: Instant,
    val categoryId: CategoryId
)

data class ExpenseId(
    val id: String,
)

data class ExpenseV2(
    val expenseId: ExpenseId,
    val amount: BigDecimal,
    val description: String,
    val paidAt: Instant,
    val categoryId: CategoryId
)

data class ExpenseV2WithCategory(
    val expenseId: ExpenseId,
    val amount: BigDecimal,
    val description: String,
    val paidAt: Instant,
    val categoryId: CategoryId,
    val categoryName: String,
)
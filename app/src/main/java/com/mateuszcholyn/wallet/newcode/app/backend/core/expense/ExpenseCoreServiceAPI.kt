package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import java.math.BigDecimal
import java.time.LocalDateTime

interface ExpenseCoreServiceAPI {
    fun add(addExpenseParameters: AddExpenseParameters): ExpenseV2
    fun remove(expenseId: ExpenseId)
    fun getAll(): List<ExpenseV2>
    fun update(updateExpenseParameters: ExpenseV2): ExpenseV2
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

data class ExpenseV2(
    val id: ExpenseId,
    val amount: BigDecimal,
    var description: String,
    var paidAt: LocalDateTime,
    var categoryId: CategoryId
)
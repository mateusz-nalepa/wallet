package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

fun randomCategoryId(): CategoryId = CategoryId("categoryId-${randomUUID()}")
fun randomCategoryName(): String = "categoryName-${randomUUID()}"
fun randomAmount(): BigDecimal = BigDecimal.valueOf(Random().nextInt(20).toLong().toDouble())
fun randomDescription(): String = "description-${randomUUID()}"
fun randomPaidAt(): LocalDateTime {
    return LocalDateTime.now().minusDays(Random().nextInt(10).toLong())
}

fun randomInt(): Int = Random().nextInt(15)
fun randomExpenseId(): ExpenseId = ExpenseId("expenseId-${randomUUID()}")

fun BigDecimal.plusRandomValue(): BigDecimal = this + randomAmount()
fun BigDecimal.minusRandomValue(): BigDecimal = this - randomAmount()
fun BigDecimal.plusInt(int: Int): BigDecimal = this + BigDecimal(int)
package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.core.ExpenseId
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

fun randomUUID(): String = UUID.randomUUID().toString()
fun randomCategoryId(): CategoryId = CategoryId("categoryId-${randomUUID()}")
fun randomCategoryName(): String = "categoryName-${randomUUID()}"
fun randomAmount(): BigDecimal = BigDecimal.ONE
fun randomDescription(): String = "description-${randomUUID()}"
fun randomPaidAt(): LocalDateTime {
    return LocalDateTime.now().minusDays(Random().nextInt(10).toLong())
}

fun randomInt(): Int = Random().nextInt(15)
fun randomExpenseId(): ExpenseId = ExpenseId("expenseId-${randomUUID()}")

fun BigDecimal.plusRandomValue(): BigDecimal = this + BigDecimal("5")
fun BigDecimal.minusRandomValue(): BigDecimal = this - BigDecimal("5")
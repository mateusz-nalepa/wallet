package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.expense.Expense
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

fun randomString(): String = UUID.randomUUID().toString()

fun randomCategoryName(): String = "categoryName-${randomString()}"
fun randomDescription(): String = "description-${randomString()}"

fun randomNewCategory(): Category =
        Category(
                name = randomCategoryName()
        )

fun randomNewExpense(
        category: Category,
): Expense =
        Expense(
                amount = BigDecimal("5.0"),
                date = LocalDateTime.now(),
                description = randomDescription(),
                category = category,
        )

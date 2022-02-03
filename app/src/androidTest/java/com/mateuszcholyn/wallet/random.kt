package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.*

fun randomString(): String = UUID.randomUUID().toString()

fun randomCategoryName(): String = "categoryName-${randomString()}"
fun randomDescription(): String = "description-${randomString()}"

fun randomNewCategoryEntity(): CategoryEntity =
        CategoryEntity(
                name = randomCategoryName()
        )

fun randomNewCategory(): Category =
        Category(
                name = randomCategoryName()
        )

fun randomNewExpense(
        category: Category,
): Expense =
        Expense(
                amount = BigDecimal.valueOf(5),
                date = LocalDateTime.now(),
                description = randomDescription(),
                category = category,
        )

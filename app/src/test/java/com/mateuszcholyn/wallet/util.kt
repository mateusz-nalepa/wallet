package com.mateuszcholyn.wallet

import com.mateuszcholyn.wallet.backend.categorycore.CategoryId
import java.math.BigDecimal
import java.time.Instant
import java.util.*

fun randomUUID(): String = UUID.randomUUID().toString()
fun randomCategoryId(): CategoryId = CategoryId("categoryId-${randomUUID()}")
fun randomCategoryName(): String = "categoryName-${randomUUID()}"
fun randomAmount(): BigDecimal = BigDecimal.ONE
fun randomDescription(): String = "description-${randomUUID()}"
fun randomPaidAt(): Instant {
    return Instant.now().minusMillis(Random().nextLong())
}
package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.util.Random

fun randomCategoryId(): CategoryId = CategoryId("categoryId-${randomUUID()}")
fun randomCategoryName(): String = "categoryName-${randomUUID()}"

// from 5 to 25??
fun randomAmount(): BigDecimal = BigDecimal.valueOf(Random().nextInt(20) + 5.toLong().toDouble())
fun randomDescription(): String = "description-${randomUUID()}"
fun randomPaidAt(): Instant {
    val localDateTime = LocalDateTime.now().minusDays(Random().nextInt(10) - 5.toLong())
    return localDateTime.fromUserLocalTimeZoneToUTCInstant()
}

fun randomInt(): Int =
//    from 5 to 15?
    Random().nextInt(10) + 5

fun randomExpenseId(): ExpenseId = ExpenseId("expenseId-${randomUUID()}")

fun BigDecimal.plusRandomValue(): BigDecimal = this + randomAmount()
fun BigDecimal.minusRandomValue(): BigDecimal = this - randomAmount()
fun BigDecimal.plusInt(int: Int): BigDecimal = this + BigDecimal(int)

fun randomBackupCategoryV1(
    categoryId: CategoryId = randomCategoryId(),
    name: String = randomCategoryName(),
): BackupWalletV1.BackupCategoryV1 =
    BackupWalletV1.BackupCategoryV1(
        id = categoryId.id,
        name = name,
    )

fun randomBackupExpenseV1(
    expenseId: ExpenseId = randomExpenseId(),
    categoryId: CategoryId = randomCategoryId(),
): BackupWalletV1.BackupExpenseV1 =
    BackupWalletV1.BackupExpenseV1(
        expenseId = expenseId.id,
        amount = randomAmount(),
        description = randomDescription(),
        paidAt = InstantConverter.toLong(randomPaidAt()),
        categoryId = categoryId.id,
    )


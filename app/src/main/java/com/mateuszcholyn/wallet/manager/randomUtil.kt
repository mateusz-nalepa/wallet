package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.randomCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupCategoryV1
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupExpenseV1
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID
import java.math.BigDecimal
import java.time.Instant
import java.time.LocalDateTime
import java.util.Random

fun randomCategoryId(): CategoryId = CategoryId(randomUUID())
fun randomCategoryName(): String = "categoryName-${randomUUID()}"

fun randomCategory(): Category =
    Category(
        id = randomCategoryId(),
        name = randomCategoryName(),
    )

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

fun randomLong(): Long = randomInt().toLong()

fun randomExpenseId(): ExpenseId = ExpenseId(randomUUID())

fun BigDecimal.plusRandomValue(): BigDecimal = this + randomAmount()
fun BigDecimal.minusRandomValue(): BigDecimal = this - randomAmount()
fun BigDecimal.plusInt(int: Int): BigDecimal = this + BigDecimal(int)

fun randomBackupCategoryV1(
    categoryId: CategoryId = randomCategoryId(),
    name: String = randomCategoryName(),
    expenses: List<BackupExpenseV1> = emptyList(),
): BackupCategoryV1 =
    BackupCategoryV1(
        id = categoryId.id,
        name = name,
        expenses = expenses,
    )

fun randomBackupExpenseV1(
    expenseId: ExpenseId = randomExpenseId(),
): BackupExpenseV1 =
    BackupExpenseV1(
        expenseId = expenseId.id,
        amount = randomAmount(),
        description = randomDescription(),
        paidAt = InstantConverter.toLong(randomPaidAt()),
    )


fun randomBackupWalletV1(): BackupWalletV1 =
    BackupWalletV1(
        categories = listOf(
            randomBackupCategoryV1(
                expenses = listOf(
                    randomBackupExpenseV1(),
                )
            ),
        )
    )

fun randomImportV1Summary(): ImportV1Summary =
    ImportV1Summary(
        1,
        2,
        3,
        4,
        5,
        6,
    )

fun randomExpenseWithCategory(
    expenseId: ExpenseId = randomExpenseId(),
    amount: BigDecimal = randomAmount(),
    paidAt: Instant = randomPaidAt(),
    categoryQuickSummary: CategoryQuickSummary = randomCategoryQuickSummary(),
): ExpenseWithCategory =
    ExpenseWithCategory(
        expenseId = expenseId,
        amount = amount,
        description = "torquent",
        paidAt = paidAt,
        categoryId = categoryQuickSummary.categoryId,
        categoryName = categoryQuickSummary.categoryName,
    )
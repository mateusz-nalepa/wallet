package com.mateuszcholyn.wallet.frontend.infrastructure.backup.read

import java.math.BigDecimal

data class BackupSaveModelAll(
    val expenses: List<BackupSaveModel>,
)

data class BackupSaveModel(
    val expenseId: Long,
    val amount: BigDecimal,
    val categoryId: Long,
    val categoryName: String,
    val date: String,
    val description: String,
)


val ALL_EXPENSES_AS_JSON = """""".trimIndent()
package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1

import java.math.BigDecimal

data class BackupWalletVersionReader(
    val version: Int,
)

data class BackupWalletV1(
    val categories: List<BackupCategoryV1>,
) {
    val version: Int = 1

}

data class BackupCategoryV1(
    val id: String,
    val name: String,
    val expenses: List<BackupExpenseV1>,
)

data class BackupExpenseV1(
    val expenseId: String,
    val amount: BigDecimal,
    val description: String,
    val paidAt: Long,
)

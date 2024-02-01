package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1

import java.math.BigDecimal

data class BackupWalletV1(
    val version: Int,
    val categories: List<BackupCategoryV1>,
) {
    data class BackupCategoryV1(
        val id: String,
        val name: String,
        val expenses: List<BackupExpenseV1>,
    ) {
        data class BackupExpenseV1(
            val expenseId: String,
            val amount: BigDecimal,
            val description: String,
            val paidAt: Long,
        )
    }


}

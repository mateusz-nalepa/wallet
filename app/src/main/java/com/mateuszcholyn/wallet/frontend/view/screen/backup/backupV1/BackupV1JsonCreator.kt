package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.view.screen.backup.BackupObjectMapper


class BackupV1JsonCreator(
    private val categories: List<CategoryV2>,
    private val expenses: List<ExpenseV2>,
) {

    private val objectMapper =
        BackupObjectMapper
            .objectMapper
            .writerWithDefaultPrettyPrinter()

    fun createBackupWalletV1AsString(): String =
        objectMapper.writeValueAsString(createBackupWalletV1())

    private fun createBackupWalletV1(): BackupWalletV1 =
        BackupWalletV1(
            version = 1,
            categories = categories
                .map {
                    BackupWalletV1.BackupCategoryV1(
                        id = it.id.id,
                        name = it.name,
                    )
                },
            expenses = expenses.map {
                BackupWalletV1.BackupExpenseV1(
                    expenseId = it.expenseId.id,
                    amount = it.amount,
                    description = it.description,
                    paidAt = it.paidAt.toEpochMilli(),
                    categoryId = it.categoryId.id,
                )
            }
        )
}

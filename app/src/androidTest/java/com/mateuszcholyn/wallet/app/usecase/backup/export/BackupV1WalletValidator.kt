package com.mateuszcholyn.wallet.app.usecase.backup.export

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

fun BackupWalletV1.validate(validateBlock: BackupV1WalletValidator.() -> Unit) {
    BackupV1WalletValidator(this)
        .validateBlock()
}

class BackupV1WalletValidator(
    private val backupWalletV1: BackupWalletV1,
) {

    fun versionIsEqualTo1() {
        assert(backupWalletV1.version == 1) {
            "Backup version should be: 1. Actual: ${backupWalletV1.version} "
        }
    }

    fun numberOfCategoriesEqualTo(expectedNumberOfCategories: Int) {
        assert(backupWalletV1.categories.size == expectedNumberOfCategories) {
            "Categories in backup should be: $expectedNumberOfCategories, Actual: ${backupWalletV1.categories.size} "
        }
    }

    fun numberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
        val expensesSize = backupWalletV1.categories.flatMap { it.expenses }.size

        assert(expensesSize == expectedNumberOfExpenses) {
            "Expenses in backup should be: ${expectedNumberOfExpenses}, Actual: $expensesSize "
        }
    }
}

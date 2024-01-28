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
        assert(backupWalletV1.categories.size == 2) {
            "Categories in backup should be: 2, Actual: ${backupWalletV1.categories.size} "
        }
    }
    // TODO: check it XD
//    fun numberOfExpensesEqualTo(expectedNumberOfExpenses: Int) {
//        assert(backupWalletV1.expenses.size == 2) {
//            "Expenses in backup should be: 2, Actual: ${backupWalletV1.expenses.size} "
//        }
//    }
}

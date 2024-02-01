package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

data class ImportV1Parameters(
    val backupWalletV1: BackupWalletV1,
    val removeAllBeforeImport: Boolean,
    val onCategoryNameChangedAction: (OnCategoryChangedInput) -> Unit,
    val onExpanseChangedAction: (OnExpanseChangedInput) -> Unit,
)

data class OnCategoryChangedInput(
    // TODO: pass category from backup and database
    val keepCategoryFromDatabase: () -> Unit,
    val useCategoryFromBackup: () -> Unit,
)

data class OnExpanseChangedInput(
    // TODO: pass expense from backup and database
    val keepExpenseFromDatabase: () -> Unit,
    val useExpenseFromBackup: () -> Unit,
)

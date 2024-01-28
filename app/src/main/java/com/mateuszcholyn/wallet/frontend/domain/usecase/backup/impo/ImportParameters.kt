package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1

data class ImportV1Parameters(
    val backupWalletV1: BackupWalletV1,
    val removeAllBeforeImport: Boolean,
    // te dwie akcje powinny zwracać wynik, czy dla pozostałych wykonać to samo
    val onCategoryChangedAction: (OnCategoryChangedInput) -> Unit,
    val onExpanseChangedAction: (OnExpanseChangedInput) -> Unit,
)

data class OnCategoryChangedInput(
    val keepCategoryNameFromDatabase: () -> Unit,
    val skipCategoryAndAllExpenses: () -> Unit,
)

data class OnExpanseChangedInput(
    val keepExpenseFromDatabase: () -> Unit,
    val skipExpense: () -> Unit,
)

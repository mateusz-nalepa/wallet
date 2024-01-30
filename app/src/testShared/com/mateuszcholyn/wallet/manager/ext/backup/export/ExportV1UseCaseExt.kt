package com.mateuszcholyn.wallet.manager.ext.backup.export

import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.exportV1UseCase(): BackupWalletV1 =
    runBlocking {
        expenseAppUseCases
            .exportV1UseCase
            .invoke()
    }


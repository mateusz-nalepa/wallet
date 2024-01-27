package com.mateuszcholyn.wallet.manager.ext.backup.impo

import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.ExpenseAppManager

fun ExpenseAppManager.importV1UseCase(
    scope: ImportV1ParametersScope.() -> Unit,
): ImportV1Summary {

    val importV1Parameters =
        ImportV1ParametersScope()
            .apply(scope)
            .toV1Parameters()

    return this
        .expenseAppUseCases
        .importV1UseCase
        .invoke(importV1Parameters)
}


class ImportV1ParametersScope {
    lateinit var backupWalletV1: BackupWalletV1
    var removeAllBeforeImport: Boolean = false

    fun toV1Parameters(): ImportV1Parameters =
        ImportV1Parameters(
            backupWalletV1 = backupWalletV1,
            removeAllBeforeImport = removeAllBeforeImport,
        )
}
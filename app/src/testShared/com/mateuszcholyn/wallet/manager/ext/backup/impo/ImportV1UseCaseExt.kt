package com.mateuszcholyn.wallet.manager.ext.backup.impo

import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import kotlinx.coroutines.runBlocking

fun ExpenseAppManager.importV1UseCase(
    scope: ImportV1ParametersScope.() -> Unit,
): ImportV1Summary {

    val importV1Parameters =
        ImportV1ParametersScope()
            .apply(scope)
            .toV1Parameters()

    return runBlocking {
        expenseAppUseCases
            .importV1UseCase
            .invoke(importV1Parameters)
    }

}


class ImportV1ParametersScope {
    lateinit var backupWalletV1: BackupWalletV1
    var removeAllBeforeImport: Boolean = false
    var onCategoryChangedAction: (OnCategoryChangedInput) -> Unit = {
        throw RuntimeException("not used")
    }
    var onExpanseChangedAction: (OnExpanseChangedInput) -> Unit = {
        throw RuntimeException("not used")
    }

    fun toV1Parameters(): ImportV1Parameters =
        ImportV1Parameters(
            backupWalletV1 = backupWalletV1,
            removeAllBeforeImport = removeAllBeforeImport,
            onCategoryNameChangedAction = onCategoryChangedAction,
            onExpanseChangedAction = onExpanseChangedAction,
        )
}
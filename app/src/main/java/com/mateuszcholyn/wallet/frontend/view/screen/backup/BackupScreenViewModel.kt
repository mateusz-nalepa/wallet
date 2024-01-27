package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupV1JsonCreator
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupV1JsonReader
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// TODO: zrób żeby działał ten import i eksport
// i testy ofc na to
// i ofc w transakcji cały zapis XD
@HiltViewModel
class BackupScreenViewModel @Inject constructor(
    private val allBackendServices: AllBackendServices,
) : ViewModel() {

    fun createBackupV1JsonString(): String =
        BackupV1JsonCreator(
            categories = allBackendServices.categoryCoreServiceAPI.getAll(),
            expenses = allBackendServices.expenseCoreServiceAPI.getAll(),
        )
            .createBackupWalletV1AsString()

    // TODO: export jak export, ale to weź mocno obtestuj xD
    fun importBackupV1JsonString(
        jsonString: String,
        onImportSuccessAction: () -> Unit,
    ) {
        val backupWalletV1Data = BackupV1JsonReader.readBackupWalletV1(jsonString)
        onImportSuccessAction.invoke()
        println(backupWalletV1Data)
    }
}

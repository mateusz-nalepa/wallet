package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export.ExportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupV1JsonCreator
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

// TODO: zrób żeby działał ten import i eksport
// i testy ofc na to
// i ofc w transakcji cały zapis XD

sealed class ImportState {

    data object NotStarted : ImportState()
    data object Loading : ImportState()
    data class Success(val importV1Summary: ImportV1Summary) : ImportState()
    data class Error(val errorMessage: String) : ImportState()
}

@HiltViewModel
class BackupScreenViewModel @Inject constructor(
    private val exportV1UseCase: ExportV1UseCase,
    private val importV1UseCase: ImportV1UseCase,
) : ViewModel() {


    private var _importState: MutableState<ImportState> = mutableStateOf(ImportState.NotStarted)
    val importState: State<ImportState>
        get() = _importState

    fun createBackupV1JsonString(): String =
        exportV1UseCase
            .invoke()
            .let { BackupV1JsonCreator.createBackupWalletV1AsString(it) }

    // TODO: export jak export, ale to weź mocno obtestuj xD
    fun importBackupV1JsonString(
        jsonString: String,
        onCategoryChangedAction: (OnCategoryChangedInput) -> Unit,
        onExpanseChangedAction: (OnExpanseChangedInput) -> Unit,
    ) {
        try {
//            val backupWalletV1 = BackupV1JsonReader.readBackupWalletV1(jsonString)
//            println(backupWalletV1)

            val backupWalletV1 =
                BackupWalletV1(
                    version = 1,
                    emptyList(),
                )

            val importV1Parameters =
                ImportV1Parameters(
                    backupWalletV1 = backupWalletV1,
                    removeAllBeforeImport = false,
                    onCategoryChangedAction = onCategoryChangedAction,
                    onExpanseChangedAction = onExpanseChangedAction,
                )


            viewModelScope.launch {
                _importState.value = ImportState.Loading
                val importV1Summary = importV1UseCase.invoke(importV1Parameters)
                _importState.value = ImportState.Success(importV1Summary)
            }

        } catch (e: Exception) {
            _importState.value = ImportState.Error(e.message ?: "Unknown error sad times")
        }

    }
}

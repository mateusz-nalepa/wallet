package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export.ExportV1UseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.WalletMediaType
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.FileExportParameters
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ExportV1ViewModel @Inject constructor(
    private val exportV1UseCase: ExportV1UseCase,
) : ViewModel() { // done tests XD

    var exportedUiState = mutableStateOf(BackupExportUiState())
        private set
    private var uiState by MutableStateDelegate(exportedUiState)

    fun closeErrorModal() {
        uiState = uiState.copy(errorModalState = ErrorModalState.NotVisible)
    }

    fun exportBackupV1(
        onFileReadyAction: (FileExportParameters) -> Unit,
    ) {
        viewModelScope.launch { // DONE UI State
            try {
                uiState = uiState.copy(isLoading = true)
                unsafeExportData(onFileReadyAction)
                uiState = uiState.copy(isLoading = false)
            } catch (t: Throwable) {
                uiState = uiState.copy(
                    errorModalState = ErrorModalState.Visible("Nieznany błąd podczas exportu danych"),
                    isLoading = false,
                )
            }
        }
    }

    private suspend fun unsafeExportData(
        onFileReadyAction: (FileExportParameters) -> Unit,
    ) {
        uiState = uiState.copy(isLoading = true)
        val fileName = "wallet-backup-${LocalDateTime.now().toHumanDateTimeText()}.json"
        val fileContent =
            exportV1UseCase
                .invoke()
                .let { BackupV1JsonCreator.createBackupWalletV1AsString(it) }

        onFileReadyAction.invoke(
            FileExportParameters(
                fileName = fileName,
                fileContent = fileContent,
                title = "Eksport danych",
                mediaType = WalletMediaType.APPLICATION_JSON,
            )
        )
    }

}

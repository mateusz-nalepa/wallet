package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext

data class BackupExportUiState(
    val isLoading: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@Composable
fun BackupExport(
    exportV1ViewModel: ExportV1ViewModel = hiltViewModel(),
) {
    val context = currentAppContext()
    val fileExporter = fileExporter()

    val backupExportUiState by remember { exportV1ViewModel.exportedUiState }

    ActionButton(
        text = "Eksportuj dane",
        onClick = {
            exportV1ViewModel.exportBackupV1(
                context = context,
                onFileReadyAction = { fileExporter.launch(it) },
            )
        },
        isLoading = backupExportUiState.isLoading,
        errorModalState = backupExportUiState.errorModalState,
        onErrorModalClose = { exportV1ViewModel.onErrorModalClose() }
    )
}

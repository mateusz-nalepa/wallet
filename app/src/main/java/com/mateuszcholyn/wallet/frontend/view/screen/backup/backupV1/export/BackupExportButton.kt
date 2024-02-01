package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText
import java.time.LocalDateTime

@Composable
fun BackupExport(
    exportV1ViewModel: ExportV1ViewModel = hiltViewModel(),
) {
    val context = currentAppContext()
    val fileExporter = fileExporter()
    var buttonIsLoading by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }

    val onClickAction = {
        buttonIsLoading = true
        exportV1ViewModel.exportBackupV1(
            context = context,
            fileName = "wallet-backup-${LocalDateTime.now().toHumanDateTimeText()}.json",
            onFileReadyAction = {
                fileExporter.launch(it)
                buttonIsLoading = false
            },
            onErrorTextProvider = {
                errorState = ErrorModalState.Visible(it)
                buttonIsLoading = false
            }
        )
    }

    ActionButton(
        text = "Eksportuj dane",
        onClick = onClickAction,
        isLoading = buttonIsLoading,
        errorModalState = errorState,
        onErrorModalClose = {
            errorState = ErrorModalState.NotVisible
        }
    )
}


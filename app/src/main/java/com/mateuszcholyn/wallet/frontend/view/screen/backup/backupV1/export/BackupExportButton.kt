package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.export

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview

data class BackupExportUiState(
    val isLoading: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@Composable
fun BackupExport(
    exportV1ViewModel: ExportV1ViewModel = hiltViewModel(),
) {
    val fileExporter = fileExporter()

    val backupExportUiState by remember { exportV1ViewModel.exportedUiState }

    val exportLabel = stringResource(R.string.common_export_data)
    val exportFileNamePrefix = stringResource(R.string.export_file_name_prefix)

    BackupExportStateless(
        backupExportUiState = backupExportUiState,
        onClick = {
            exportV1ViewModel.exportBackupV1(
                exportLabel = exportLabel,
                exportFileNamePrefix = exportFileNamePrefix,
                onFileReadyAction = { fileExporter.launch(it) },
            )
        },
        onErrorModalClose = {
            exportV1ViewModel.closeErrorModal()
        },
    )

}

@Composable
private fun BackupExportStateless(
    backupExportUiState: BackupExportUiState,
    onClick: () -> Unit,
    onErrorModalClose: () -> Unit,
) {
    ActionButton(
        text = stringResource(R.string.backupScreen_export_data),
        onClick = onClick,
        isLoading = backupExportUiState.isLoading,
        errorModalState = backupExportUiState.errorModalState,
        onErrorModalClose = onErrorModalClose,
    )
}

@Preview(showBackground = true)
@Composable
fun BackupExportButtonStatelessDarkPreview(
    @PreviewParameter(BackupExportUiStateProvider::class) backupExportUiState: BackupExportUiState,
) {
    SetContentOnDarkPreview {
        BackupExportStateless(
            backupExportUiState,
            onClick = { },
            onErrorModalClose = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackupExportButtonStatelessLightPreview(
    @PreviewParameter(BackupExportUiStateProvider::class) backupExportUiState: BackupExportUiState,
) {
    SetContentOnLightPreview {
        BackupExportStateless(
            backupExportUiState,
            onClick = { },
            onErrorModalClose = { },
        )
    }
}

class BackupExportUiStateProvider : PreviewParameterProvider<BackupExportUiState> {
    override val values: Sequence<BackupExportUiState> =
        sequenceOf(
            BackupExportUiState(
                isLoading = false,
                errorModalState = ErrorModalState.NotVisible,
            ),
        )
}

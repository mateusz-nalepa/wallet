package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModal
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalDialogState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.externalFileToInternal
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.readFileContent
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.buttonPadding
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.userConfig.priceFormatterConfig.PriceFormatterParametersConfig

@Composable
fun BackupImport(
    importV1ViewModel: ImportV1ViewModel = hiltViewModel(),
) {
    val context = currentAppContext()
    val backupImportUiState by remember { importV1ViewModel.exportedUiState }

    val noDescriptionLabel = stringResource(R.string.common_noDescription)

    val priceFormatterParameters =
        PriceFormatterParametersConfig.getPriceFormatterParameters(context)

    val fileSelector =
        fileSelector(
            onExternalFileSelected = { externalFileUri ->
                importV1ViewModel.importBackupV1(
                    bigDecimalAsFormattedAmountFunction = {
                        it.asPrintableAmount(priceFormatterParameters)
                    },
                    noDescriptionLabel = noDescriptionLabel,
                ) {
                    context
                        .externalFileToInternal(externalFileUri)
                        .readFileContent()
                }
            }
        )

    BackupImportButtonStateless(
        backupImportUiState = backupImportUiState,
        onImportClick = { fileSelector.launch() },
        onErrorModalClose = { importV1ViewModel.closeErrorStateModal() },
        onSuccessModalClose = { importV1ViewModel.closeSuccessStateModal() },
        onComparatorModalDialogClosed = { importV1ViewModel.closeComparatorModalDialog() }
    )
}

@Composable
fun BackupImportButtonStateless(
    backupImportUiState: BackupImportUiState,
    onImportClick: () -> Unit,
    onSuccessModalClose: () -> Unit,
    onErrorModalClose: () -> Unit,
    onComparatorModalDialogClosed: () -> Unit,
) {
    ComparatorModal(
        comparatorModalDialogState = backupImportUiState.compareModalParameters,
        onComparatorModalDialogClosed = onComparatorModalDialogClosed,
    )

    Column {
        ActionButton(
            text = stringResource(R.string.backupScreen_import_data),
            onClick = onImportClick,
            isLoading = backupImportUiState.buttonIsLoading,
            errorModalState = backupImportUiState.errorState,
            onErrorModalClose = onErrorModalClose,
            successModalState = backupImportUiState.successState,
            successModalContent = {
                if (backupImportUiState.successState is SuccessModalState.Visible) ImportSummaryStateless(
                    importV1Summary = backupImportUiState.successState.importV1Summary
                )
            },
            onSuccessModalClose = onSuccessModalClose,
        )
        ImportV1SummaryProgressStateless(backupImportUiState.importV1SummaryProgressState)
    }
}

@Composable
fun ImportV1SummaryProgressStateless(
    importV1SummaryProgressState: ImportV1SummaryProgressState?,
) {
    if (importV1SummaryProgressState == null) {
        return
    }
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = defaultModifier
            .fillMaxWidth()
            .padding(horizontal = buttonPadding)
    ) {
        Text(text = "${stringResource(R.string.backupScreen_import_progress)} ${importV1SummaryProgressState.percentageProgress}%")
    }
    Divider()
}


@Preview(showBackground = true)
@Composable
fun BackupImportButtonStatelessDarkPreview(@PreviewParameter(BackupImportUiStateProvider::class) backupImportUiState: BackupImportUiState) {
    SetContentOnDarkPreview {
        BackupImportButtonStateless(
            backupImportUiState,
            onImportClick = { },
            onErrorModalClose = { },
            onSuccessModalClose = { },
            onComparatorModalDialogClosed = { },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackupImportButtonStatelessLightPreview(@PreviewParameter(BackupImportUiStateProvider::class) backupImportUiState: BackupImportUiState) {
    SetContentOnLightPreview {
        BackupImportButtonStateless(
            backupImportUiState,
            onImportClick = { },
            onErrorModalClose = { },
            onSuccessModalClose = { },
            onComparatorModalDialogClosed = { },
        )
    }
}


class BackupImportUiStateProvider : PreviewParameterProvider<BackupImportUiState> {
    override val values: Sequence<BackupImportUiState> =
        sequenceOf(
            BackupImportUiState(
                buttonIsLoading = false,
                errorState = ErrorModalState.NotVisible,
                successState = SuccessModalState.NotVisible,
                compareModalParameters = ComparatorModalDialogState.NotVisible,
                importV1SummaryProgressState = ImportV1SummaryProgressState(50),
            ),
        )
}

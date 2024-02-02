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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.backup.CategoryChangedModal
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ExpenseChangedModal
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.buttonPadding
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@Composable
fun BackupImport(
    importV1ViewModel: ImportV1ViewModel = hiltViewModel(),
) {
    val context = currentAppContext()

    var buttonIsLoading by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }
    var successState by remember { mutableStateOf<SuccessModalState>(SuccessModalState.NotVisible) }


    val categoryModalDialogIsVisible = remember { mutableStateOf(false) }
    var onKeepCategoryFromDatabase: () -> Unit by remember { mutableStateOf({}) }
    var onUseCategoryFromBackup: () -> Unit by remember { mutableStateOf({}) }

    CategoryChangedModal(
        categoryModalDialogIsVisible,
        onKeepCategoryFromDatabase,
        onUseCategoryFromBackup,
    )

    val expenseModalDialogIsVisible = remember { mutableStateOf(false) }
    var onKeepExpenseFromDatabase: () -> Unit by remember { mutableStateOf({}) }
    var onUseExpenseFromBackup: () -> Unit by remember { mutableStateOf({}) }


    ExpenseChangedModal(
        expenseModalDialogIsVisible,
        onKeepExpenseFromDatabase,
        onUseExpenseFromBackup,
    )

    var importV1SummaryProgressState by remember { mutableStateOf<ImportV1Summary?>(null) }

    val fileSelector =
        fileSelector(
            onExternalFileSelected = { externalFileUri ->
                buttonIsLoading = true
                importV1ViewModel.importBackupV1(
                    context = context,
                    externalFileUri = externalFileUri,
                    onImportProgress = { importV1SummaryProgress ->
                        importV1SummaryProgressState = importV1SummaryProgress
                    },
                    onCategoryChangedAction = {
                        onKeepCategoryFromDatabase = it.keepCategoryFromDatabase
                        onUseCategoryFromBackup = it.useCategoryFromBackup
                        categoryModalDialogIsVisible.value = true
                    },
                    onExpanseChangedAction = {
                        onKeepExpenseFromDatabase = it.keepExpenseFromDatabase
                        onUseExpenseFromBackup = it.useExpenseFromBackup
                        categoryModalDialogIsVisible.value = true
                    },
                    onSuccessAction = {
                        successState = SuccessModalState.Visible {
                            ImportSummaryStateless(it)
                        }
                        buttonIsLoading = false
                        importV1SummaryProgressState = null
                    },
                    onErrorTextProvider = {
                        errorState = ErrorModalState.Visible(it)
                        buttonIsLoading = false
                        importV1SummaryProgressState = null
                    }
                )
            }
        )

    BackupImportButtonStateless(
        onImportClick = { fileSelector.launch() },
        buttonIsLoading = buttonIsLoading,
        errorModalState = errorState,
        onErrorModalClose = { errorState = ErrorModalState.NotVisible },
        successModalState = successState,
        onSuccessModalClose = { successState = SuccessModalState.NotVisible },
        importV1SummaryProgressState = importV1SummaryProgressState,
    )
}

@Composable
fun BackupImportButtonStateless(
    buttonIsLoading: Boolean,
    onImportClick: () -> Unit,
    errorModalState: ErrorModalState,
    onErrorModalClose: () -> Unit,
    successModalState: SuccessModalState,
    onSuccessModalClose: () -> Unit,
    importV1SummaryProgressState: ImportV1Summary?,
) {
    Column(modifier = defaultModifier) {
        ActionButton(
            text = "Importuj dane",
            onClick = { onImportClick.invoke() },
            isLoading = buttonIsLoading,
            errorModalState = errorModalState,
            onErrorModalClose = { onErrorModalClose.invoke() },
            successModalState = successModalState,
            onSuccessModalClose = { onSuccessModalClose.invoke() }
        )
        ImportV1SummaryProgressStateless(importV1SummaryProgressState)
    }
}

@Composable
fun ImportV1SummaryProgressStateless(
    importV1SummaryProgressState: ImportV1Summary?,
) {
    if (importV1SummaryProgressState == null) {
        return
    }
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = buttonPadding)
    ) {
        Text(text = "Imported records")
        Text(text = importV1SummaryProgressState.categoriesProgress())
    }
    Divider()
}

private fun ImportV1Summary.categoriesProgress() =
    "${
        numberOfImportedCategories + numberOfSkippedCategories +
            numberOfImportedExpenses + numberOfSkippedExpenses
    } of ${numberOfCategories + numberOfExpenses}"


@Preview(showBackground = true)
@Composable
fun BackupImportButtonStatelessDarkPreview(@PreviewParameter(ImportV1SummaryProvider::class) importV1Summary: ImportV1Summary) {
    SetContentOnDarkPreview {
        BackupImportButtonStateless(
            buttonIsLoading = false,
            onImportClick = {},
            errorModalState = ErrorModalState.NotVisible,
            onErrorModalClose = { },
            successModalState = SuccessModalState.NotVisible,
            onSuccessModalClose = {},
            importV1SummaryProgressState = importV1Summary
        )
    }
}

@Preview(showBackground = true)
@Composable
fun BackupImportButtonStatelessLightPreview(@PreviewParameter(ImportV1SummaryProvider::class) importV1Summary: ImportV1Summary) {
    SetContentOnLightPreview {
        BackupImportButtonStateless(
            buttonIsLoading = false,
            onImportClick = {},
            errorModalState = ErrorModalState.NotVisible,
            onErrorModalClose = { },
            successModalState = SuccessModalState.NotVisible,
            onSuccessModalClose = {},
            importV1SummaryProgressState = importV1Summary
        )
    }
}



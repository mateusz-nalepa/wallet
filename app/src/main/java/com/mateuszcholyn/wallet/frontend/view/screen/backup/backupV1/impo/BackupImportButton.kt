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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.backup.CategoryChangedModal
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ExpenseChangedModal
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MySuccessDialog
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext

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

    val fileSelector =
        fileSelector(
            onExternalFileSelected = { externalFileUri ->
                buttonIsLoading = true
                importV1ViewModel.importBackupV1(
                    context = context,
                    externalFileUri = externalFileUri,
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
                    },
                    onErrorTextProvider = {
                        errorState = ErrorModalState.Visible(it)
                        buttonIsLoading = false
                    }
                )
            }
        )

    ActionButton(
        text = "Importuj dane",
        onClick = { fileSelector.launch() },
        isLoading = buttonIsLoading,
        errorModalState = errorState,
        onErrorModalClose = { errorState = ErrorModalState.NotVisible },
        successModalState = successState,
        onSuccessModalClose = { successState = SuccessModalState.NotVisible }
    )
}

@Composable
fun ImportSummaryStateless(importV1Summary: ImportV1Summary) {
    Column {
        ImportSummaryRowStateless("Categories total", importV1Summary.numberOfCategories)
        ImportSummaryRowStateless("Expenses total", importV1Summary.numberOfExpenses)
        ImportSummaryRowStateless("Imported categories", importV1Summary.numberOfImportedCategories)
        ImportSummaryRowStateless("Skipped categories", importV1Summary.numberOfSkippedCategories)
        ImportSummaryRowStateless("Imported expenses", importV1Summary.numberOfImportedExpenses)
        ImportSummaryRowStateless("Skipped expenses", importV1Summary.numberOfSkippedExpenses)
    }
}

@Composable
private fun ImportSummaryRowStateless(
    text: String,
    number: Int,
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(1.dp)
    ) {
        Text(text = text)
        Text(text = "$number")
    }
    Divider()
}

@Preview(showBackground = true)
@Composable
fun MySuccessDialogDarkPreview() {
    val importV1Summary =
        ImportV1Summary(
            1,
            2,
            3,
            4,
            5, 6
        )
    SetContentOnDarkPreview {
        MySuccessDialog(
            onClose = {},
        ) {
            ImportSummaryStateless(importV1Summary)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MySuccessDialogLightPreview() {
    val importV1Summary =
        ImportV1Summary(
            1,
            2,
            3,
            4,
            5, 6
        )
    SetContentOnLightPreview {
        MySuccessDialog(
            onClose = {},
        ) {
            ImportSummaryStateless(importV1Summary)
        }
    }
}



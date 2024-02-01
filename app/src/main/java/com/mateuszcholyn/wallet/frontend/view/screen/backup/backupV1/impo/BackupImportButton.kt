package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.view.screen.backup.categoryChangedModal
import com.mateuszcholyn.wallet.frontend.view.screen.backup.expenseChangedModal
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext

@Composable
fun BackupImport(
    importV1ViewModel: ImportV1ViewModel = hiltViewModel(),
) {
    val context = currentAppContext()

    var buttonIsLoading by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }
    var successState by remember { mutableStateOf<SuccessModalState>(SuccessModalState.NotVisible) }

    val categoryChangedModal = categoryChangedModal()
    val expenseChangedModal = expenseChangedModal()

    val fileSelector =
        fileSelector(
            onExternalFileSelected = { externalFileUri ->
                buttonIsLoading = true
                importV1ViewModel.importBackupV1(
                    context = context,
                    externalFileUri = externalFileUri,
                    onCategoryChangedAction = { categoryChangedModal.open(it) },
                    onExpanseChangedAction = { expenseChangedModal.open(it) },
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
    Text(text = importV1Summary.toString())
}

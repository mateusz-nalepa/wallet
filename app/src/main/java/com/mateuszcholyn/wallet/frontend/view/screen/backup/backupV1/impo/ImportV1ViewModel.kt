package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalDialogState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.externalFileToInternal
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.readFileContent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


data class ImportV1SummaryProgressState(
    val percentageProgress: Int,
)

data class BackupImportUiState(
    val buttonIsLoading: Boolean = false,
    val errorState: ErrorModalState = ErrorModalState.NotVisible,
    val successState: SuccessModalState = SuccessModalState.NotVisible,

    val compareModalParameters: ComparatorModalDialogState = ComparatorModalDialogState.NotVisible,

    val importV1SummaryProgressState: ImportV1SummaryProgressState? = null,
)

@HiltViewModel
class ImportV1ViewModel @Inject constructor(
    private val importV1UseCase: ImportV1UseCase,
) : ViewModel() {
    var exportedUiState = mutableStateOf(BackupImportUiState())
        private set
    private var uiState by MutableStateDelegate(exportedUiState)

    fun closeSuccessStateModal() {
        uiState = uiState.copy(successState = SuccessModalState.NotVisible)
    }

    fun closeErrorStateModal() {
        uiState = uiState.copy(errorState = ErrorModalState.NotVisible)
    }

    fun closeComparatorModalDialog() {
        uiState = uiState.copy(compareModalParameters = ComparatorModalDialogState.NotVisible)
    }

    fun importBackupV1(
        context: Context,
        externalFileUri: Uri,
    ) {
        uiState = uiState.copy(buttonIsLoading = true)

        importBackupV1(
            context = context,
            externalFileUri = externalFileUri,
            onImportProgress = { importV1Summary ->
                uiState = uiState.copy(importV1SummaryProgressState = importV1Summary.toImportV1SummaryProgressState())
            },
            onCategoryChangedAction = {
                uiState = uiState.copy(
                    compareModalParameters = ComparatorModalDialogState.Visible(it.toComparableDataModalParameters())
                )
            },
            onExpanseChangedAction = {
                uiState = uiState.copy(
                    compareModalParameters = ComparatorModalDialogState.Visible(it.toComparableDataModalParameters())
                )
            },
            onSuccessAction = {
                uiState = uiState.copy(
                    successState = SuccessModalState.Visible(it),
                    buttonIsLoading = false,
                    importV1SummaryProgressState = null,
                )
            },
            onErrorTextProvider = {
                uiState = uiState.copy(
                    errorState = ErrorModalState.Visible(it),
                    buttonIsLoading = false,
                    importV1SummaryProgressState = null,
                )
            }
        )
    }

    private fun importBackupV1(
        context: Context,
        externalFileUri: Uri,
        onCategoryChangedAction: (OnCategoryChangedInput) -> Unit,
        onExpanseChangedAction: (OnExpanseChangedInput) -> Unit,
        onSuccessAction: (ImportV1Summary) -> Unit,
        onErrorTextProvider: (String) -> Unit,
        onImportProgress: (ImportV1Summary) -> Unit,
    ) {
        viewModelScope.launch { // DONE
            try {
                println(uiState)
                uiState = uiState.copy(buttonIsLoading = false)

                val backupWalletV1 =
                    context
                        .externalFileToInternal(externalFileUri)
                        .readFileContent()
                        .let { BackupV1JsonReader.readBackupWalletV1(it) }
                val importV1Parameters =
                    ImportV1Parameters(
                        onImportProgress = onImportProgress,
                        backupWalletV1 = backupWalletV1,
                        removeAllBeforeImport = false,
                        onCategoryNameChangedAction = onCategoryChangedAction,
                        onExpanseChangedAction = onExpanseChangedAction,
                    )
                val importV1Summary = importV1UseCase.invoke(importV1Parameters)
                onSuccessAction.invoke(importV1Summary)
            } catch (e: Exception) {
                onErrorTextProvider.invoke("Nieznany błąd podczas importu danych")
            }
        }
    }

}

private fun ImportV1Summary.toImportV1SummaryProgressState(): ImportV1SummaryProgressState {
    val recordsProgress =
        numberOfImportedCategories +
            numberOfSkippedCategories +
            numberOfImportedExpenses +
            numberOfSkippedExpenses

    val recordsTotal = numberOfCategories + numberOfExpenses

    return ImportV1SummaryProgressState(percentageProgress = calculatePercentage(recordsProgress, recordsTotal))
}

private fun calculatePercentage(
    actual: Int,
    total: Int
): Int {
    val percentage = (actual.toDouble() / total) * 100
    return percentage.toInt()
}

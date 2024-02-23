package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalDialogState
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.util.PercentageCalculator.calculatePercentage
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
) : ViewModel() { // done tests XD
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
        noDescriptionLabel: String,
        fileContentReader: () -> String,
    ) {
        viewModelScope.launch { // DONE UI State
            try {
                uiState = uiState.copy(buttonIsLoading = false)
                val importV1Summary = unsafeImportData(noDescriptionLabel, fileContentReader.invoke())
                uiState = uiState.copy(
                    successState = SuccessModalState.Visible(importV1Summary),
                    buttonIsLoading = false,
                    importV1SummaryProgressState = null,
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    errorState = ErrorModalState.Visible(R.string.error_unable_to_import_data),
                    buttonIsLoading = false,
                    importV1SummaryProgressState = null,
                )
            }
        }
    }

    private suspend fun unsafeImportData(
        noDescriptionLabel: String,
        fileContent: String,
    ): ImportV1Summary {
        val backupWalletV1 = BackupV1JsonReader.readBackupWalletV1(fileContent)
        return importV1UseCase.invoke(createImportV1Parameters(noDescriptionLabel, backupWalletV1))
    }

    private fun createImportV1Parameters(
        noDescriptionLabel: String,
        backupWalletV1: BackupWalletV1,
    ): ImportV1Parameters =
        ImportV1Parameters(
            onImportProgress = {
                uiState = uiState.copy(importV1SummaryProgressState = it.toImportV1SummaryProgressState())
            },
            backupWalletV1 = backupWalletV1,
            removeAllBeforeImport = false,
            onCategoryNameChangedAction = {
                uiState = uiState.copy(
                    compareModalParameters = ComparatorModalDialogState.Visible(it.toComparableDataModalParameters())
                )
            },
            onExpanseChangedAction = {
                uiState = uiState.copy(
                    compareModalParameters = ComparatorModalDialogState.Visible(it.toComparableDataModalParameters(noDescriptionLabel))
                )
            },
        )
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

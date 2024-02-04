package com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Parameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalDialogState
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.BackupWalletV1
import com.mateuszcholyn.wallet.frontend.view.screen.backup.backupV1.impo.PercentageCalculator.calculatePercentage
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
        viewModelScope.launch { // DONE
            try {
                uiState = uiState.copy(buttonIsLoading = false)
                val importV1Summary = unsafeImportData(context, externalFileUri)
                uiState = uiState.copy(
                    successState = SuccessModalState.Visible(importV1Summary),
                    buttonIsLoading = false,
                    importV1SummaryProgressState = null,
                )
            } catch (e: Exception) {
                uiState = uiState.copy(
                    errorState = ErrorModalState.Visible("Nieznany błąd podczas importu danych"),
                    buttonIsLoading = false,
                    importV1SummaryProgressState = null,
                )
            }
        }
    }

    private suspend fun unsafeImportData(
        context: Context,
        externalFileUri: Uri,
    ): ImportV1Summary {
        val backupWalletV1 =
            context
                .externalFileToInternal(externalFileUri)
                .readFileContent()
                .let { BackupV1JsonReader.readBackupWalletV1(it) }
        return importV1UseCase.invoke(createImportV1Parameters(backupWalletV1))
    }

    private fun createImportV1Parameters(
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
                    compareModalParameters = ComparatorModalDialogState.Visible(it.toComparableDataModalParameters())
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

object PercentageCalculator {
    fun calculatePercentage(
        actual: Int,
        total: Int
    ): Int {
        val percentage = (actual.toDouble() / total) * 100
        return percentage.toInt()
    }
}

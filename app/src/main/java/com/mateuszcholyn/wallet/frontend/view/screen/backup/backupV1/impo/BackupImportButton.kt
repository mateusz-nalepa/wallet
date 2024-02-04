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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1Summary
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparableData
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModal
import com.mateuszcholyn.wallet.frontend.view.screen.backup.ComparatorModalParameters
import com.mateuszcholyn.wallet.frontend.view.screen.summary.orDefaultDescription
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ActionButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.SuccessModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.buttonPadding
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText

private fun OnCategoryChangedInput.toComparableDataModalParameters(): ComparatorModalParameters =
    ComparatorModalParameters(
        title = "Kategoria się zmieniła! Co zrobić?",
        leftValuesQuickSummary = "Stan z kopii",
        rightValuesQuickSummary = "Aktualny stan",

        keepLeftText = "Użyj danych z kopii",
        onKeepLeft = useCategoryFromBackup,

        keepRightText = "Zachowaj istniejący",
        onKeepRight = keepCategoryFromDatabase,

        comparableData = listOf(
            ComparableData(
                "Nazwa",
                categoriesToCompare.categoryFromBackup.categoryName,
                categoriesToCompare.categoryFromDatabase.categoryName,
            ),
        ),
    )

private fun OnExpanseChangedInput.toComparableDataModalParameters(): ComparatorModalParameters =
    ComparatorModalParameters(
        title = "Wydatek się zmienił! Co zrobić?",
        leftValuesQuickSummary = "Stan z kopii",
        rightValuesQuickSummary = "Aktualny stan",

        keepLeftText = "Użyj danych z kopii",
        onKeepLeft = useExpenseFromBackup,

        keepRightText = "Zachowaj istniejący",
        onKeepRight = keepExpenseFromDatabase,

        comparableData = listOf(
            ComparableData(
                "Kategoria",
                expensesToCompare.expenseFromBackup.categoryName,
                expensesToCompare.expenseFromDatabase.categoryName,
            ),
            ComparableData(
                "Kwota",
                expensesToCompare.expenseFromBackup.amount.asPrintableAmount(),
                expensesToCompare.expenseFromDatabase.amount.asPrintableAmount(),
            ),
            ComparableData(
                "Data",
                expensesToCompare.expenseFromBackup.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText(),
                expensesToCompare.expenseFromDatabase.paidAt.fromUTCInstantToUserLocalTimeZone().toHumanDateTimeText(),
            ),
            ComparableData(
                "Opis",
                expensesToCompare.expenseFromBackup.description.orDefaultDescription("Brak opisu"),
                expensesToCompare.expenseFromDatabase.description.orDefaultDescription("Brak opisu"),
            ),
        ),
    )

@Composable
fun BackupImport(
    importV1ViewModel: ImportV1ViewModel = hiltViewModel(),
) {
    val context = currentAppContext()

    // TODO: te wszystkie stany to kurdę z viewModelu powinny być brane XD
    var buttonIsLoading by remember { mutableStateOf(false) }
    var errorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }
    var successState by remember { mutableStateOf<SuccessModalState>(SuccessModalState.NotVisible) }


    val categoryModalDialogIsVisible = remember { mutableStateOf(false) }
    var compareCategoryModalParameters by remember { mutableStateOf<ComparatorModalParameters?>(null) }

    ComparatorModal(
        categoryModalDialogIsVisible,
        compareCategoryModalParameters
    )

    val expenseModalDialogIsVisible = remember { mutableStateOf(false) }
    var compareExpenseModalParameters by remember { mutableStateOf<ComparatorModalParameters?>(null) }

    ComparatorModal(
        expenseModalDialogIsVisible,
        compareExpenseModalParameters,
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
                        compareCategoryModalParameters = it.toComparableDataModalParameters()
                        categoryModalDialogIsVisible.value = true
                    },
                    onExpanseChangedAction = {
                        compareExpenseModalParameters = it.toComparableDataModalParameters()
                        expenseModalDialogIsVisible.value = true
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
    Column {
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
        horizontalArrangement = Arrangement.Center,
        modifier = defaultModifier
            .fillMaxWidth()
            .padding(horizontal = buttonPadding)
    ) {
        Text(text = "Progress ${importV1SummaryProgressState.progress()}")
    }
    Divider()
}

private fun ImportV1Summary.progress(): String {
    val recordsProgress =
        numberOfImportedCategories +
            numberOfSkippedCategories +
            numberOfImportedExpenses +
            numberOfSkippedExpenses

    val recordsTotal = numberOfCategories + numberOfExpenses
    return "${calculatePercentage(recordsProgress, recordsTotal)}%"
}

private fun calculatePercentage(
    actual: Int,
    total: Int
): Int {
    val percentage = (actual.toDouble() / total) * 100
    return percentage.toInt()
}

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



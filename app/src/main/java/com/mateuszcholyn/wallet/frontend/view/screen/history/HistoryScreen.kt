package com.mateuszcholyn.wallet.frontend.view.screen.history

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.HistoryFilters
import com.mateuszcholyn.wallet.frontend.view.screen.history.results.HistorySearchResultStateless
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.skeleton.copyExpenseRoute
import com.mateuszcholyn.wallet.frontend.view.skeleton.editExpenseRoute
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.time.LocalDateTime

data class HistoryScreenActions(
    val onAdvancedFiltersVisible: (Boolean) -> Unit,

    val onCategorySelected: (CategoryView) -> Unit,
    val onQuickRangeDataSelected: (QuickRangeData) -> Unit,
    val onBeginDateChanged: (LocalDateTime) -> Unit,
    val onEndDateChanged: (LocalDateTime) -> Unit,

    val onSortElementSelected: (SortElement) -> Unit,
    val onGroupingCheckboxChanged: (Boolean) -> Unit,
    val onGroupingElementSelected: (GroupElement) -> Unit,
    val onAmountRangeStartChanged: (String) -> Unit,
    val onAmountRangeEndChanged: (String) -> Unit,
    // Copy
    val onCopySingleExpenseAction: (ExpenseId) -> Unit,
    // Edit
    val onEditSingleExpenseAction: (ExpenseId) -> Unit,
    // Remove - TO BE DONE
    val onShowRemovalDialog: () -> Unit,
    val onConfirmRemoveAction: (ExpenseId) -> Unit,
    val onRemovalDialogClosed: () -> Unit,
    // Refresh results
    val refreshResultsAction: () -> Unit,
    // Error
    val onErrorModalClose: () -> Unit,
    // Export
    val onExportHistory: () -> Unit,
    val onExportErrorModalClose: () -> Unit,
)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun HistoryScreen(
    navHostController: NavHostController,
    historyScreenViewModel: HistoryScreenViewModel = hiltViewModel(),
) {
    val fileExporter = fileExporter()

    val wholeSummaryScreenState by remember { historyScreenViewModel.exposedHistoryScreenState }
    val exposedSummarySearchForm by remember { historyScreenViewModel.exposedHistorySearchForm }
    val summaryResultState by remember { historyScreenViewModel.exposedHistoryResultState }
    val removeSingleExpenseUiState by remember { historyScreenViewModel.exposedRemoveUiState }
    val exportUiState by remember { historyScreenViewModel.exportedExportUiState }

    DisposableEffect(key1 = Unit, effect = {
        historyScreenViewModel.initScreen()
        onDispose { }
    })

    val historyScreenActions = HistoryScreenActions(
        onCategorySelected = {
            historyScreenViewModel.updateSelectedCategory(it)
        },
        onQuickRangeDataSelected = {
            historyScreenViewModel.updateQuickRangeData(it)
        },
        onBeginDateChanged = {
            historyScreenViewModel.updateBeginDate(it)
        },
        onEndDateChanged = {
            historyScreenViewModel.updateEndDate(it)
        },
        onSortElementSelected = {
            historyScreenViewModel.updateSortElement(it)
        },
        onGroupingCheckboxChanged = {
            historyScreenViewModel.updateGroupingCheckBoxChecked(it)
        },
        onGroupingElementSelected = {
            historyScreenViewModel.updateGroupElement(it)
        },
        onAmountRangeStartChanged = {
            historyScreenViewModel.updateAmountRangeStart(it)
        },
        onAmountRangeEndChanged = {
            historyScreenViewModel.updateAmountRangeEnd(it)
        },
        onCopySingleExpenseAction = { expenseId ->
            navHostController.navigate(
                copyExpenseRoute(expenseId = expenseId)
            )
        },
        onEditSingleExpenseAction = { expenseId ->
            navHostController.navigate(
                editExpenseRoute(expenseId = expenseId)
            )
        },
        refreshResultsAction = {
            historyScreenViewModel.loadResultsFromDb()
        },
        onShowRemovalDialog = {
            historyScreenViewModel.showRemoveConfirmationDialog()
        },
        onConfirmRemoveAction = {
            historyScreenViewModel.removeExpenseById(it)
        },
        onRemovalDialogClosed = {
            historyScreenViewModel.closeRemoveModalDialog()
        },
        onErrorModalClose = {
            historyScreenViewModel.closeErrorModalDialog()
        },
        onAdvancedFiltersVisible = {
            historyScreenViewModel.updateAdvancedFiltersVisible(it)
        },
        onExportHistory = {
            historyScreenViewModel.exportToCsv(
                onFileReadyAction = {
                    fileExporter.launch(it)
                }
            )
        },
        onExportErrorModalClose = {
            historyScreenViewModel.closeExportErrorModalDialog()
        }

    )

    HistoryScreenStateless(
        historyScreenState = wholeSummaryScreenState,
        exposedHistorySearchForm = exposedSummarySearchForm,
        historyResultState = summaryResultState,
        historyScreenActions = historyScreenActions,
        removeSingleExpenseUiState = removeSingleExpenseUiState,
        exportUiState = exportUiState,
    )
}

@Composable
fun HistoryScreenStateless(
    historyScreenState: HistoryScreenState,
    exposedHistorySearchForm: HistorySearchForm,
    historyResultState: HistoryResultState,
    historyScreenActions: HistoryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
    exportUiState: ExportToCsvUiState,
) {
    when (historyScreenState) {
        is HistoryScreenState.Error -> ScreenError(historyScreenState.message)
        HistoryScreenState.Loading -> ScreenLoading()
        is HistoryScreenState.Visible -> {
            HistoryScreenStateless(
                historySearchForm = exposedHistorySearchForm,
                historyResultState = historyResultState,
                historyScreenActions = historyScreenActions,
                removeSingleExpenseUiState = removeSingleExpenseUiState,
                exportUiState = exportUiState,
            )
        }
    }
}

@Composable
fun HistoryScreenStateless(
    historySearchForm: HistorySearchForm,
    historyResultState: HistoryResultState,
    historyScreenActions: HistoryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
    exportUiState: ExportToCsvUiState,
) {

    MyErrorDialogProxy(
        errorModalState = removeSingleExpenseUiState.errorModalState,
        onErrorModalClose = historyScreenActions.onErrorModalClose,
    )
    MyErrorDialogProxy(
        errorModalState = exportUiState.errorModalState,
        onErrorModalClose = historyScreenActions.onExportErrorModalClose,
    )
    Column(modifier = defaultModifier) {
        HistoryFilters(
            historySearchForm = historySearchForm,
            historyScreenActions = historyScreenActions,
            exportUiState = exportUiState,
        )
        Divider()
        HistorySearchResultStateless(
            historySearchForm = historySearchForm,
            historyResultState = historyResultState,
            historyScreenActions = historyScreenActions,
            removeSingleExpenseUiState = removeSingleExpenseUiState,
        )
    }
}

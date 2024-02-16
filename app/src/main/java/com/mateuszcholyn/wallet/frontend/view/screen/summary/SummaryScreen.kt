package com.mateuszcholyn.wallet.frontend.view.screen.summary

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
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.SummaryFilters
import com.mateuszcholyn.wallet.frontend.view.screen.summary.results.SummarySearchResultStateless
import com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.frontend.view.skeleton.copyExpense
import com.mateuszcholyn.wallet.frontend.view.skeleton.routeWithId
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

data class SummaryScreenActions(
    val onAdvancedFiltersVisible: (Boolean) -> Unit,

    val onCategorySelected: (CategoryView) -> Unit,
    val onQuickRangeDataSelected: (QuickRangeData) -> Unit,
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
)

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SummaryScreen(
    navHostController: NavHostController,
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    val wholeSummaryScreenState by remember { summaryScreenViewModel.exposedWholeSummaryScreenState }
    val exposedSummarySearchForm by remember { summaryScreenViewModel.exposedSummarySearchForm }
    val summaryResultState by remember { summaryScreenViewModel.exposedSummaryResultState }
    val removeSingleExpenseUiState by remember { summaryScreenViewModel.exposedRemoveUiState }

    DisposableEffect(key1 = Unit, effect = {
        summaryScreenViewModel.initScreen()
        onDispose { }
    })

    val summaryScreenActions = SummaryScreenActions(
        onCategorySelected = {
            summaryScreenViewModel.updateSelectedCategory(it)
        },
        onQuickRangeDataSelected = {
            summaryScreenViewModel.updateQuickRangeData(it)
        },
        onSortElementSelected = {
            summaryScreenViewModel.updateSortElement(it)
        },
        onGroupingCheckboxChanged = {
            summaryScreenViewModel.updateGroupingCheckBoxChecked(it)
        },
        onGroupingElementSelected = {
            summaryScreenViewModel.updateGroupElement(it)
        },
        onAmountRangeStartChanged = {
            summaryScreenViewModel.updateAmountRangeStart(it)
        },
        onAmountRangeEndChanged = {
            summaryScreenViewModel.updateAmountRangeEnd(it)
        },
        // TODO: data nie jest taka jak powinna XD
        onCopySingleExpenseAction = { expenseId ->
            navHostController.navigate(
                NavDrawerItem.AddOrEditExpense.copyExpense(expenseId = expenseId)
            )
        },
        onEditSingleExpenseAction = { expenseId ->
            navHostController.navigate(
                NavDrawerItem.AddOrEditExpense.routeWithId(expenseId = expenseId)
            )
        },
        refreshResultsAction = {
            summaryScreenViewModel.loadResultsFromDb()
        },
        onShowRemovalDialog = {
            summaryScreenViewModel.showRemoveConfirmationDialog()
        },
        onConfirmRemoveAction = {
            summaryScreenViewModel.removeExpenseById(it)
        },
        onRemovalDialogClosed = {
            summaryScreenViewModel.closeRemoveModalDialog()
        },
        onErrorModalClose = {
            summaryScreenViewModel.closeErrorModalDialog()
        },
        onAdvancedFiltersVisible = {
            summaryScreenViewModel.updateAdvancedFiltersVisible(it)
        }
    )

    SummaryScreenStateless(
        wholeSummaryScreenState = wholeSummaryScreenState,
        exposedSummarySearchForm = exposedSummarySearchForm,
        summaryResultState = summaryResultState,
        summaryScreenActions = summaryScreenActions,
        removeSingleExpenseUiState = removeSingleExpenseUiState,
    )
}

@Composable
fun SummaryScreenStateless(
    wholeSummaryScreenState: WholeSummaryScreenState,
    exposedSummarySearchForm: SummarySearchForm,
    summaryResultState: SummaryResultState,
    summaryScreenActions: SummaryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    when (wholeSummaryScreenState) {
        is WholeSummaryScreenState.Error -> ScreenError(wholeSummaryScreenState.message)
        WholeSummaryScreenState.Loading -> ScreenLoading()
        is WholeSummaryScreenState.Visible -> {
            SummaryScreenStateless(
                summarySearchForm = exposedSummarySearchForm,
                summaryResultState = summaryResultState,
                summaryScreenActions = summaryScreenActions,
                removeSingleExpenseUiState = removeSingleExpenseUiState,
            )
        }
    }
}

@Composable
fun SummaryScreenStateless(
    summarySearchForm: SummarySearchForm,
    summaryResultState: SummaryResultState,
    summaryScreenActions: SummaryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {

    MyErrorDialogProxy(
        errorModalState = removeSingleExpenseUiState.errorModalState,
        onErrorModalClose = summaryScreenActions.onErrorModalClose,
    )
    Column(modifier = defaultModifier) {
        SummaryFilters(
            summarySearchForm = summarySearchForm,
            summaryScreenActions = summaryScreenActions,
        )
        Divider()
        SummarySearchResultStateless(
            summarySearchForm = summarySearchForm,
            summaryResultState = summaryResultState,
            summaryScreenActions = summaryScreenActions,
            removeSingleExpenseUiState = removeSingleExpenseUiState,
        )
    }
}


//@ExperimentalFoundationApi
//@OptIn(ExperimentalMaterialApi::class)
//@Preview(showBackground = true)
//@Composable
//fun SummaryScreenPreview() {
//    SummaryScreenStateless(rememberNavController())
//}
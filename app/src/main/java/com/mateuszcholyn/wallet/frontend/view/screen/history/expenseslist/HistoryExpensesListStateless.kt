package com.mateuszcholyn.wallet.frontend.view.screen.history.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HistoryExpensesListStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
    successContent: HistorySuccessContent,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    // FIXME: grouped expenses should be in Summary View
    if (historySearchForm.isGroupingEnabled) {
        GroupedExpenses(
            historyScreenActions = historyScreenActions,
            expensesListGrouped = successContent.expensesGrouped,
            groupNameFunction = historySearchForm.selectedGroupingElement.groupFunctionName,
        )
    } else {
        ExpensesListStateless(
            historyScreenActions = historyScreenActions,
            expensesList = successContent.expensesList,
            removeSingleExpenseUiState = removeSingleExpenseUiState,
        )
    }
}
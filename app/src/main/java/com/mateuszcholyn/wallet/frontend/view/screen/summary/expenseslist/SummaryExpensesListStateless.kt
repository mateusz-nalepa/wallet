package com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove.RemoveSingleExpenseUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SummaryExpensesListStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
    successContent: SummarySuccessContent,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    if (summarySearchForm.isGroupingEnabled) {
        GroupedExpenses(
            summaryScreenActions = summaryScreenActions,
            expensesListGrouped = successContent.expensesGrouped,
            groupNameFunction = summarySearchForm.selectedGroupElement.groupFunctionName,
        )
    } else {
        ExpensesListStateless(
            summaryScreenActions = summaryScreenActions,
            expensesList = successContent.expensesList,
            removeSingleExpenseUiState = removeSingleExpenseUiState,
        )
    }
}
package com.mateuszcholyn.wallet.ui.screen.summary.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.ui.screen.summary.SummarySuccessContent
import com.mateuszcholyn.wallet.ui.screen.summary.SummaryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SummaryExpensesList(
    navController: NavHostController,
    summarySuccessContent: SummarySuccessContent,
    summaryViewModel: SummaryViewModel = hiltViewModel()
) {

    if (summaryViewModel.summarySearchForm.isGroupingEnabled) {
        GroupedExpenses(
            navController = navController,
            refreshFunction = { summaryViewModel.refreshScreen() },
            expensesListGrouped = summarySuccessContent.expensesGrouped,
            groupNameFunction = summaryViewModel.summarySearchForm.selectedGroupElement.groupFunctionName,
        )
    } else {
        ExpensesList(
            navController = navController,
            refreshFunction = { summaryViewModel.refreshScreen() },
            expensesList = summarySuccessContent.expensesList,
        )
    }
}
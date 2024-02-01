package com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SummaryExpensesList(
    navController: NavHostController,
    summarySuccessContent: SummarySuccessContent,
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel()
) {

    if (summaryScreenViewModel.summarySearchForm.isGroupingEnabled) {
        GroupedExpenses(
            navController = navController,
            refreshFunction = { summaryScreenViewModel.refreshResults() },
            expensesListGrouped = summarySuccessContent.expensesGrouped,
            groupNameFunction = summaryScreenViewModel.summarySearchForm.selectedGroupElement.groupFunctionName,
        )
    } else {
        ExpensesList(
            navController = navController,
            refreshFunction = { summaryScreenViewModel.refreshResults() },
            expensesList = summarySuccessContent.expensesList,
        )
    }
}
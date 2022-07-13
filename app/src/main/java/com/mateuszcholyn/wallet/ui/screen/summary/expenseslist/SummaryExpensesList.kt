package com.mateuszcholyn.wallet.ui.screen.summary.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.ui.screen.summary.SummaryViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SummaryExpensesList(
    navController: NavHostController,
    summaryViewModel: SummaryViewModel = hiltViewModel()
) {
    if (summaryViewModel.isGroupingEnabled) {
        GroupedExpenses(
            navController = navController,
            refreshFunction = { summaryViewModel.refreshScreen() },
            expensesListGrouped = summaryViewModel.expensesListGrouped,
            groupNameFunction = summaryViewModel.selectedGroupElement.groupFunctionName,
        )
    } else {
        ExpensesList(
            navController = navController,
            refreshFunction = { summaryViewModel.refreshScreen() },
            expensesList = summaryViewModel.expensesList,
        )
    }
}
package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.ui.screen.summary.advancedFilters.AdvancedFiltersSection
import com.mateuszcholyn.wallet.ui.screen.summary.expenseslist.SummaryExpensesList
import com.mateuszcholyn.wallet.ui.util.defaultModifier


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NewSummaryScreen(
    navController: NavHostController,
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {

    summaryViewModel.initScreen()
    Column(modifier = defaultModifier) {
        SummaryCategoriesSection()
        SummaryQuickRangeSection()
        AdvancedFiltersSection()
        Divider()
        SummaryStatisticSection()
        Divider()
        SummaryExpensesList(navController = navController)
    }

}


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewSummaryScreenPreview() {
    NewSummaryScreen(rememberNavController())
}
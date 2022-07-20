package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
        SummaryFilters()
        Divider()
        SummarySearchResult(navController)
    }

}

@Composable
fun SummaryFilters() {
    SummaryCategoriesSection()
    SummaryQuickRangeSection()
    AdvancedFiltersSection()
}

@Composable
fun SummarySearchResult(
    navController: NavHostController,
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {
    val summaryState by remember { summaryViewModel.summaryState }

    when (val summaryStateTemp = summaryState) {
        is SummaryState.Error -> SummaryError(summaryStateTemp.errorMessage)
        is SummaryState.Loading -> SummaryLoading()
        is SummaryState.Success -> {
            SuccessSearchResult(
                navController = navController,
                successContent = summaryStateTemp.summarySuccessContent
            )
        }
    }
}


@Composable
fun SuccessSearchResult(
    navController: NavHostController,
    successContent: SummarySuccessContent,
) {
    SummaryStatisticSection(successContent)
    Divider()
    SummaryExpensesList(
        navController = navController,
        summarySuccessContent = successContent,
    )
}

@Composable
fun SummaryLoading() {
    Row(
        modifier = defaultModifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = "Summary Loading")
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun SummaryLoadingPreview() {
    SummaryLoading()
}

@Composable
fun SummaryError(errorMsg: String) {
    Text(text = "Summary error: $errorMsg")
}

@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewSummaryScreenPreview() {
    NewSummaryScreen(rememberNavController())
}
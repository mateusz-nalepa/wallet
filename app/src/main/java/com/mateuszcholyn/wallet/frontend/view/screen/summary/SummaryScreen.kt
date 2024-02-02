package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.SummaryFilters
import com.mateuszcholyn.wallet.frontend.view.screen.summary.results.SummarySearchResult
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SummaryScreen(
    navController: NavHostController,
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    DisposableEffect(key1 = Unit, effect = {
        summaryScreenViewModel.initScreen()
        onDispose { }
    })
    Column(modifier = defaultModifier) {
        SummaryFilters()
        Divider()
        SummarySearchResult(navController)
    }

}


@ExperimentalFoundationApi
@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewSummaryScreenPreview() {
    SummaryScreen(rememberNavController())
}
package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.SummaryFilters
import com.mateuszcholyn.wallet.frontend.view.screen.summary.results.SummarySearchResult
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier

// TODO: dodaj modala jak jest tryb demo, co i jak XD

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun SummaryScreen(
    navController: NavHostController,
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {

    summaryScreenViewModel.initScreen()
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
package com.mateuszcholyn.wallet.frontend.view.screen.summary.results

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryResultState
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenViewModel
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist.SummaryExpensesList
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading

@Composable
fun SummarySearchResult(
    navController: NavHostController,
    summaryScreenViewModel: SummaryScreenViewModel = hiltViewModel(),
) {
    val summaryState by remember { summaryScreenViewModel.summaryResultState }

    when (val summaryStateTemp = summaryState) {
        is SummaryResultState.Error -> ScreenError(summaryStateTemp.errorMessage)
        is SummaryResultState.Loading -> ScreenLoading()
        is SummaryResultState.Success -> {
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


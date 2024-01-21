package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
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
import com.mateuszcholyn.wallet.SelectedColorsMutableState
import com.mateuszcholyn.wallet.UserStore
import com.mateuszcholyn.wallet.frontend.view.screen.summary.advancedFilters.AdvancedFiltersSection
import com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist.SummaryExpensesList
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NewSummaryScreen(
    navController: NavHostController,
    summaryViewModel: SummaryViewModel = hiltViewModel(),
) {

    val store = UserStore(currentAppContext())

    summaryViewModel.initScreen()
    Column(modifier = defaultModifier) {
        Button(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    store.saveSelectedTheme(
                        if (SelectedColorsMutableState.value == "light") {
                            SelectedColorsMutableState.value = "dark"
                            "dark"
                        } else {
                            SelectedColorsMutableState.value = "light"
                            "light"
                        }

                    )
                }
            }
        ) {
            Text("Zmien motyw")
        }
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
        is SummaryState.Loading -> ScreenLoading("Summary Loading")
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
fun ScreenLoading(loadingText: String) {
    Row(
        modifier = defaultModifier.fillMaxSize(),
        horizontalArrangement = Arrangement.Center
    ) {
        Text(text = loadingText)
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun SummaryLoadingPreview() {
    ScreenLoading("XD")
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
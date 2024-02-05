package com.mateuszcholyn.wallet.frontend.view.screen.summary.results

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryResultState
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummarySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist.SummaryExpensesListStateless
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading

@Composable
fun SummarySearchResultStateless(
    summarySearchForm: SummarySearchForm,
    summaryResultState: SummaryResultState,
    summaryScreenActions: SummaryScreenActions,
) {
    when (summaryResultState) {
        is SummaryResultState.Error -> ScreenError(summaryResultState.errorMessage)
        is SummaryResultState.Loading -> ScreenLoading()
        is SummaryResultState.Success -> {
            SuccessSearchResultStateless(
                summarySearchForm = summarySearchForm,
                summaryScreenActions = summaryScreenActions,
                successContent = summaryResultState.summarySuccessContent
            )
        }
    }
}


@Composable
fun SuccessSearchResultStateless(
    summarySearchForm: SummarySearchForm,
    summaryScreenActions: SummaryScreenActions,
    successContent: SummarySuccessContent,
) {
    SummaryStatisticSectionStateless(successContent)
    Divider()
    SummaryExpensesListStateless(
        summarySearchForm = summarySearchForm,
        summaryScreenActions = summaryScreenActions,
        successContent = successContent,
    )
}


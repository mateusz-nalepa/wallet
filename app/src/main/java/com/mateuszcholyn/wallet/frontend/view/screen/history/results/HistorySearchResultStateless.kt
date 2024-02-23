package com.mateuszcholyn.wallet.frontend.view.screen.history.results

import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryResultState
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySearchForm
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistorySuccessContent
import com.mateuszcholyn.wallet.frontend.view.screen.history.expenseslist.HistoryExpensesListStateless
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenError.ScreenError
import com.mateuszcholyn.wallet.frontend.view.screen.util.screenLoading.ScreenLoading

@Composable
fun HistorySearchResultStateless(
    historySearchForm: HistorySearchForm,
    historyResultState: HistoryResultState,
    historyScreenActions: HistoryScreenActions,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    when (historyResultState) {
        is HistoryResultState.Error -> ScreenError(historyResultState.errorMessageKey)
        is HistoryResultState.Loading -> ScreenLoading()
        is HistoryResultState.Success -> {
            SuccessSearchResultStateless(
                historySearchForm = historySearchForm,
                historyScreenActions = historyScreenActions,
                successContent = historyResultState.historySuccessContent,
                removeSingleExpenseUiState = removeSingleExpenseUiState
            )
        }
    }
}


@Composable
fun SuccessSearchResultStateless(
    historySearchForm: HistorySearchForm,
    historyScreenActions: HistoryScreenActions,
    successContent: HistorySuccessContent,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    HistoryStatisticSectionStateless(successContent)
    Divider()
    HistoryExpensesListStateless(
        historySearchForm = historySearchForm,
        historyScreenActions = historyScreenActions,
        successContent = successContent,
        removeSingleExpenseUiState = removeSingleExpenseUiState,
    )
}


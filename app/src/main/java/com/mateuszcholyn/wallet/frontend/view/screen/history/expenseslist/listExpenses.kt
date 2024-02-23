package com.mateuszcholyn.wallet.frontend.view.screen.history.expenseslist

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.ShowSingleExpense
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.util.BOTTOM_BAR_HEIGHT

@Composable
fun ExpensesListStateless(
    historyScreenActions: HistoryScreenActions,
    expensesList: List<SearchSingleResult>,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = BOTTOM_BAR_HEIGHT),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        itemsIndexed(items = expensesList) { index, searchSingleResult ->
            ShowSingleExpense(
                index = index + 1,
                searchSingleResult = searchSingleResult,
                historyScreenActions = historyScreenActions,
                removeSingleExpenseUiState = removeSingleExpenseUiState,
            )
        }
    }

}
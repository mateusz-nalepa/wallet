package com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.ShowSingleExpense
import com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove.RemoveSingleExpenseUiState

@Composable
fun ExpensesListStateless(
    summaryScreenActions: SummaryScreenActions,
    expensesList: List<SearchSingleResult>,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {

//    TODO coś takiego zrób dla tej listy XD
//    val sortedContacts = remember(contacts, comparator) {
//        contacts.sortedWith(comparator)
//    }

    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        itemsIndexed(items = expensesList) { id, searchSingleResult ->
            ShowSingleExpense(
                id = id,
                searchSingleResult = searchSingleResult,
                summaryScreenActions = summaryScreenActions,
                removeSingleExpenseUiState = removeSingleExpenseUiState,
            )
        }
    }

}
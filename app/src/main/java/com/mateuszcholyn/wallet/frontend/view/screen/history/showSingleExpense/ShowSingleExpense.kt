package com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Paid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmountWithoutDollar
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier


@Composable
fun ShowSingleExpense(
    historyScreenActions: HistoryScreenActions,
    index: Int,
    searchSingleResult: SearchSingleResult,
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
) {
    var detailsAreVisible by remember { mutableStateOf(false) }
    QuickExpenseSummary(
        onClickAction = {
            detailsAreVisible = !detailsAreVisible
        },
        index = index,
        searchSingleResult = searchSingleResult,
    )
    if (detailsAreVisible) {
        ShowSingleExpenseDetails(
            searchSingleResult,
            historyScreenActions,
            removeSingleExpenseUiState,
        )
    }
    Divider()
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun QuickExpenseSummary(
    onClickAction: () -> Unit,
    index: Int,
    searchSingleResult: SearchSingleResult,
) {
    ListItem(
        text = { Text("${index}. ${searchSingleResult.categoryName}") },
        trailing = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = searchSingleResult.amount.asPrintableAmountWithoutDollar(), fontSize = 16.sp)
                Icon(
                    Icons.Filled.Paid,
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        },
        modifier = defaultModifier.clickable {
            onClickAction.invoke()
        },
    )
}
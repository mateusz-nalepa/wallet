package com.mateuszcholyn.wallet.frontend.view.screen.history.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.util.BOTTOM_BAR_HEIGHT
import java.math.BigDecimal


@ExperimentalFoundationApi
@Composable
fun GroupedExpenses(
    historyScreenActions: HistoryScreenActions,
    expensesListGrouped: Map<String, List<SearchSingleResult>>,
    groupNameFunction: (SearchSingleResult) -> String,
) {
    val expenses = expensesListGrouped.toList()

    LazyColumn(
        contentPadding = PaddingValues(bottom = BOTTOM_BAR_HEIGHT),
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
    ) {
        itemsIndexed(items = expenses) { index, expensePair ->
            ShowSingleGroupedExpense(
                historyScreenActions = historyScreenActions,
                expensesInGroup = expensePair.second,
                groupNameFunction = groupNameFunction,
            )
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowSingleGroupedExpense(
    historyScreenActions: HistoryScreenActions,
    expensesInGroup: List<SearchSingleResult>,
    groupNameFunction: (SearchSingleResult) -> String,
) {
    ListItem(
        text = {
            Text(
                text = groupNameFunction.invoke(expensesInGroup.first()),
            )
        },
        trailing = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = historyScreenActions.onFormatPrice.invoke(expensesInGroup.sumExpensesAmount()),
                    fontSize = 16.sp,
                )
            }
        },
    )

    Divider()
}

// FIXME: this function should not be present here
private fun List<SearchSingleResult>.sumExpensesAmount(): BigDecimal =
    if (this.isNotEmpty()) {
        this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
    } else {
        BigDecimal.ZERO
    }

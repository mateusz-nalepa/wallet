package com.mateuszcholyn.wallet.frontend.view.screen.history.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.math.BigDecimal


@ExperimentalFoundationApi
@Composable
fun GroupedExpenses(
    expensesListGrouped: Map<String, List<SearchSingleResult>>,
    groupNameFunction: (SearchSingleResult) -> String,
) {
    LazyColumn(modifier = defaultModifier) {
        expensesListGrouped.forEach { (_, expensesInGroup) ->
            stickyHeader {
                Row(
                    modifier = defaultModifier
                        .background(MaterialTheme.colors.onPrimary)
                        .padding(0.dp), horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = groupNameFunction.invoke(expensesInGroup.first()),
                        modifier = defaultModifier.weight(1f)
                    )
                    Text(
                        text = expensesInGroup.sumExpensesAmount().asPrintableAmount(),
                        modifier = defaultModifier.weight(1f)
                    )
                }
            }
        }
    }
}

// FIXME: this function should not be present here
private fun List<SearchSingleResult>.sumExpensesAmount(): BigDecimal =
    if (this.isNotEmpty()) {
        this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
    } else {
        BigDecimal.ZERO
    }

package com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist

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
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.math.BigDecimal


@ExperimentalFoundationApi
@Composable
// TODO: jak jest grupowanie i sortowanie to ten widok generalnie nie ma sensu XDD
fun GroupedExpenses(
    summaryScreenActions: SummaryScreenActions,
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
            // TODO: czy ja aby na pewno tego potrzebuje? XD
            // bez tego w sumie to wygląda jak podsumowanie i też jest fajnie w sumie XD
            // ewentualnie booleana na to czy wyświetlać to czy nie XD


//            itemsIndexed(items = expensesInGroup) { id, searchSingleResult ->
//                ShowSingleExpense(
//                    id = id,
//                    searchSingleResult = searchSingleResult,
//                    summaryScreenActions = summaryScreenActions,
//                )
//            }
        }
    }
}

// TODO: ta funkcja nie powinna tutaj być XD
private fun List<SearchSingleResult>.sumExpensesAmount(): BigDecimal =
    if (this.isNotEmpty()) {
        this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
    } else {
        BigDecimal.ZERO
    }

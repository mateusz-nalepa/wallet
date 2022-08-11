package com.mateuszcholyn.wallet.ui.screen.summary.expenseslist

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.domain.expense.sumExpensesAmount
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.ui.screen.summary.ShowExpense
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrintableAmount


@ExperimentalFoundationApi
@Composable
fun GroupedExpenses(
    navController: NavHostController,
    refreshFunction: () -> Unit,
    expensesListGrouped: Map<String, List<SearchSingleResult>>,
    groupNameFunction: (SearchSingleResult) -> String,
) {
    LazyColumn(modifier = defaultModifier) {
        expensesListGrouped.forEach { (_, expensesInGroup) ->
            stickyHeader {
                Row(
                    modifier = defaultModifier
                        .background(Color.LightGray)
                        .padding(0.dp), horizontalArrangement = Arrangement.SpaceBetween
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
            itemsIndexed(items = expensesInGroup) { id, searchSingleResult ->
                ShowExpense(
                    id = id,
                    searchSingleResult = searchSingleResult,
                    navController = navController,
                    refreshFunction = { refreshFunction() }
                )
            }
        }
    }
}
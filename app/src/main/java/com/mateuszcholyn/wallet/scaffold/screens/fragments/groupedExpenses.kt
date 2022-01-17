package com.mateuszcholyn.wallet.scaffold.screens.fragments

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.sumExpensesAmount
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrinteableAmount

@ExperimentalFoundationApi
@Composable
fun GroupedExpenses(
        navController: NavHostController,
        refreshFunction: () -> Unit,
        expensesListGrouped: Map<String, List<Expense>>,
        groupNameFunction: (Expense) -> String,
) {
    LazyColumn(modifier = defaultModifier) {
        expensesListGrouped.forEach { (_, expensesInGroup) ->
            stickyHeader {
                Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = groupNameFunction.invoke(expensesInGroup.first()), modifier = defaultModifier.weight(1f))
                    Text(text = expensesInGroup.sumExpensesAmount().asPrinteableAmount(), modifier = defaultModifier.weight(1f))
                }
            }
            itemsIndexed(items = expensesInGroup) { id, expense ->
                ShowExpense(
                        id = id,
                        expense = expense,
                        navController = navController,
                        refreshFunction = { refreshFunction() }
                )
            }
        }
    }
}
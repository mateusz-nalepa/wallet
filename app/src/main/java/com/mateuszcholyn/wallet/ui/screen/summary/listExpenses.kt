package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.domain.expense.Expense

@Composable
fun ExpensesList(
    navController: NavHostController,
    refreshFunction: () -> Unit,
    expensesList: List<Expense>,
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),

        ) {
        itemsIndexed(items = expensesList) { id, expense ->
            ShowExpense(
                id = id,
                expense = expense,
                navController = navController,
                refreshFunction = refreshFunction,
            )
        }
    }

}
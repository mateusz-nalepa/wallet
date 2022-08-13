package com.mateuszcholyn.wallet.frontend.view.screen.summary.expenseslist

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.view.screen.summary.ShowExpense

@Composable
fun ExpensesList(
    navController: NavHostController,
    refreshFunction: () -> Unit,
    expensesList: List<SearchSingleResult>,
) {
    LazyColumn(
        modifier =
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),

        ) {
        itemsIndexed(items = expensesList) { id, searchSingleResult ->
            ShowExpense(
                id = id,
                searchSingleResult = searchSingleResult,
                navController = navController,
                refreshFunction = refreshFunction,
            )
        }
    }

}
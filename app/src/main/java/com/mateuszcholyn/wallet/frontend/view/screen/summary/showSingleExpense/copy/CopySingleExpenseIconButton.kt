package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.copy

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.frontend.view.skeleton.copyExpense

@Composable
fun CopySingleExpenseIconButton(
    navHostController: NavHostController,
    expenseId: ExpenseId,
) {
    CopySingleExpenseIconButtonStateless(
        onClick = {
            navHostController.navigate(
                NavDrawerItem.AddOrEditExpense.copyExpense(
                    expenseId = expenseId.id
                )
            )
        }
    )
}

@Composable
fun CopySingleExpenseIconButtonStateless(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Icon(
            Icons.Filled.CopyAll,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}
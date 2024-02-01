package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.edit

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.frontend.view.skeleton.routeWithId

@Composable
fun EditSingleExpenseIconButton(
    navController: NavHostController,
    expenseId: ExpenseId,
) {
    EditSingleExpenseIconButtonStateless(
        onClick = {
            navController.navigate(
                NavDrawerItem.AddOrEditExpense.routeWithId(
                    expenseId = expenseId.id,
                )
            )

        },
    )
}

@Composable
fun EditSingleExpenseIconButtonStateless(
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick
    ) {
        Icon(
            Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

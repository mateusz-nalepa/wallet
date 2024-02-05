package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.edit

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions

@Composable
fun EditSingleExpenseIconButtonStateless(
    expenseId: ExpenseId,
    summaryScreenActions: SummaryScreenActions,
) {
    IconButton(
        onClick = {
            summaryScreenActions.onEditSingleExpenseAction.invoke(expenseId)
        }
    ) {
        Icon(
            Icons.Filled.Edit,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

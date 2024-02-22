package com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.copy

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions

@Composable
fun CopySingleExpenseIconButtonStateless(
    expenseId: ExpenseId,
    historyScreenActions: HistoryScreenActions,
) {
    IconButton(
        onClick = {
            historyScreenActions.onCopySingleExpenseAction.invoke(expenseId)
        },
    ) {
        Icon(
            Icons.Filled.CopyAll,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

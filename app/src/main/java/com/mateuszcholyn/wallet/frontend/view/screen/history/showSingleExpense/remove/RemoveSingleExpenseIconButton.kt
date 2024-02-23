package com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.history.HistoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState


data class RemoveSingleExpenseUiState(
    val isRemovalDialogVisible: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@Composable
fun RemoveSingleExpenseIconButton(
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
    expenseId: ExpenseId,
    historyScreenActions: HistoryScreenActions,
) {


    YesOrNoDialog(
        openDialog = removeSingleExpenseUiState.isRemovalDialogVisible,
        onDialogClosed = historyScreenActions.onRemovalDialogClosed,
        content = {
            Text(text = stringResource(R.string.areYouSureYouWantToRemoveExpense))
        },
        onConfirm = {
            historyScreenActions.onConfirmRemoveAction.invoke(expenseId)
        },
        onCancel = historyScreenActions.onRemovalDialogClosed,
    )

    IconButton(
        onClick = {
            historyScreenActions.onShowRemovalDialog.invoke()
        }
    ) {
        Icon(
            Icons.Filled.DeleteForever,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}


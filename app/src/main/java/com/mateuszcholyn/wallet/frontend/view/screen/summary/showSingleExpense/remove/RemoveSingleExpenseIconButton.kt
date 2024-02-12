package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState


data class RemoveSingleExpenseUiState(
    val isRemovalDialogVisible: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@Composable
fun RemoveSingleExpenseIconButton(
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
    expenseId: ExpenseId,
    summaryScreenActions: SummaryScreenActions,
) {


    YesOrNoDialog(
        openDialog = removeSingleExpenseUiState.isRemovalDialogVisible,
        onDialogClosed = summaryScreenActions.onRemovalDialogClosed,
        content = {
            Text(text = "Usuwam wydatek XD")
        },
        onConfirm = {
            summaryScreenActions.onConfirmRemoveAction.invoke(expenseId)
        },
        onCancel = summaryScreenActions.onRemovalDialogClosed,
    )

    IconButton(
        onClick = {
            summaryScreenActions.onShowRemovalDialog.invoke()
        }
    ) {
        Icon(
            Icons.Filled.DeleteForever,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

//
//@Preview(showBackground = true)
//@Composable
//fun RemoveSingleExpenseIconButtonStatelessDarkPreview() {
//    SetContentOnDarkPreview {
//        RemoveSingleExpenseIconButtonStateless(
//            removeSingleExpenseUiState = RemoveSingleExpenseUiState(),
//            onRemovalDialogClosed = { },
//            text = "Na pewno chcesz usunąć?",
//            onShowRemovalDialog = {},
//            onConfirmRemoveAction = { },
//            onErrorModalStateClosed = {},
//        )
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun RemoveSingleExpenseIconButtonStatelessLightPreview() {
//    SetContentOnLightPreview {
//        RemoveSingleExpenseIconButtonStateless(
//            removeSingleExpenseUiState = RemoveSingleExpenseUiState(),
//            onRemovalDialogClosed = { },
//            text = "Na pewno chcesz usunąć?",
//            onShowRemovalDialog = {},
//            onConfirmRemoveAction = { },
//            onErrorModalStateClosed = {},
//        )
//    }
//}

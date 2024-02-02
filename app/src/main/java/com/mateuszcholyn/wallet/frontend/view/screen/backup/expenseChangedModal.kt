package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog

@Composable
fun ExpenseChangedModal(
    expenseModalDialogIsVisible: MutableState<Boolean>,
    onKeepExpenseFromDatabase: () -> Unit,
    onUseExpenseFromBackup: () -> Unit,
) {
    YesOrNoDialog(
        message = "Wydatek się zmienił!",
        confirmText = "Zachowaj wydatek z bazy",
        cancelText = "Zachowaj wydatek z kopii zapasowej",
        openDialog = expenseModalDialogIsVisible,
        onConfirm = {
            onKeepExpenseFromDatabase.invoke()
        },
        onCancel = {
            onUseExpenseFromBackup.invoke()
        }
    )

}

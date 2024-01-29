package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnExpanseChangedInput
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog

@Composable
fun expenseChangedModal(): OnExpenseChangedModalLauncher {

    val openExpenseChangedDialog = remember { mutableStateOf(false) }
    var onConfirmAction: () -> Unit = {}
    var onCancelAction: () -> Unit = {}
    YesOrNoDialog(
        message = "Wydatek się zmienił!",
        confirmText = "Zachowaj wydatek z bazy",
        cancelText = "Zachowaj wydatek z kopii zapasowej",
        openDialog = openExpenseChangedDialog,
        onConfirm = {
            onConfirmAction.invoke()
        },
        onCancel = {
            onCancelAction.invoke()
        }
    )

    return OnExpenseChangedModalLauncher { expenseChangedInput ->
        onConfirmAction = expenseChangedInput.keepExpenseFromDatabase
        onCancelAction = expenseChangedInput.useExpenseFromBackup
        openExpenseChangedDialog.value = true
    }

}

class OnExpenseChangedModalLauncher(
    private val launcher: (OnExpanseChangedInput) -> Unit,
) {
    fun open(onExpanseChangedInput: OnExpanseChangedInput) {
        launcher.invoke(onExpanseChangedInput)
    }
}

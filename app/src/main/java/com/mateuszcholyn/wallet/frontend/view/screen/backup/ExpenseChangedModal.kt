package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview

@Composable
fun ExpenseChangedModal(
    expenseModalDialogIsVisible: MutableState<Boolean>,
//    expenseFromBackup: Expense,
//    expenseFromDatabaseBackup: Expense,
    onKeepExpenseFromDatabase: () -> Unit,
    onUseExpenseFromBackup: () -> Unit,
) {
    YesOrNoDialog(
        content = {
            Comparator()
        },
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

@Composable
fun Comparator() {
    Text(text = "comparator")

}

@Preview(showBackground = true)
@Composable
fun ComparatorDarkPreview() {
    SetContentOnDarkPreview {
        ExpenseChangedModal(
            remember {
                mutableStateOf(true)
            },
            onKeepExpenseFromDatabase = {},
            onUseExpenseFromBackup = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComparatorLightPreview() {
    SetContentOnLightPreview {
        ExpenseChangedModal(
            remember {
                mutableStateOf(true)
            },
            onKeepExpenseFromDatabase = {},
            onUseExpenseFromBackup = {},
        )
    }
}

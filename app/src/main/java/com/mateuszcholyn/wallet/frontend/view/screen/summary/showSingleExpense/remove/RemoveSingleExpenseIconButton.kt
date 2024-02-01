package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogV2


@Composable
fun RemoveSingleExpenseIconButton(
    removeSingleExpenseViewModel: RemoveSingleExpenseViewModel = hiltViewModel(),
    expenseId: ExpenseId,
    onSuccessAction: () -> Unit,
) {
    val removeConfirmationDialog = remember { mutableStateOf(false) }
    var isDeleteLoading by remember { mutableStateOf(false) }

    var errorModalState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }

    MyErrorDialogV2(
        errorModalState = errorModalState,
        onErrorModalClose = { errorModalState = ErrorModalState.NotVisible }
    )

    YesOrNoDialog(
        openDialog = removeConfirmationDialog,
        onConfirm = {
            isDeleteLoading = true
            removeSingleExpenseViewModel.removeExpenseById(
                expenseId = expenseId,
                onSuccessAction = onSuccessAction,
                onErrorAction = {
                    isDeleteLoading = false
                    errorModalState = ErrorModalState.Visible(it)
                }
            )
        },
        onCancel = {
            isDeleteLoading = false
        }
    )

    RemoveSingleExpenseIconButtonStateless(
        isDeleteLoading = isDeleteLoading,
        onClickAction = {
            removeConfirmationDialog.value = true
        }
    )
}

@Composable
fun RemoveSingleExpenseIconButtonStateless(
    onClickAction: () -> Unit,
    isDeleteLoading: Boolean,
) {
    IconButton(
        onClick = onClickAction
    ) {
        if (isDeleteLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colors.primary
            )
        } else {
            Icon(
                Icons.Filled.DeleteForever,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
            )
        }
    }
}
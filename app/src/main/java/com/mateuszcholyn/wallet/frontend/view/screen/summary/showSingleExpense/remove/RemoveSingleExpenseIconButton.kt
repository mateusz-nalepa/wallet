package com.mateuszcholyn.wallet.frontend.view.screen.summary.showSingleExpense.remove

import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.summary.SummaryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnDarkPreview
import com.mateuszcholyn.wallet.frontend.view.screen.util.preview.SetContentOnLightPreview


data class RemoveSingleExpenseUiState(
    val isRemovalDialogVisible: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@Composable
fun RemoveSingleExpenseIconButton(
    // TODO: Now this component is not stateless :(
    removeSingleExpenseViewModel: RemoveSingleExpenseViewModel = hiltViewModel(),
    expenseId: ExpenseId,
    summaryScreenActions: SummaryScreenActions,
) {
    val removeUiState by remember { removeSingleExpenseViewModel.exposedUiState }

    RemoveSingleExpenseIconButtonStateless(
        removeSingleExpenseUiState = removeUiState,
        text = stringResource(R.string.areYouReadyToRemoveExpense),
        onShowRemovalDialog = {
            removeSingleExpenseViewModel.showRemoveConfirmationDialog()
        },
        onConfirmRemoveAction = {
            removeSingleExpenseViewModel.removeExpenseById(expenseId, summaryScreenActions.refreshResultsAction)
        },
        onRemovalDialogClosed = {
            removeSingleExpenseViewModel.closeRemoveModalDialog()
        },
        onErrorModalStateClosed = {
            removeSingleExpenseViewModel.closeErrorModalDialog()
        },
    )
}

@Composable
fun RemoveSingleExpenseIconButtonStateless(
    removeSingleExpenseUiState: RemoveSingleExpenseUiState,
    onShowRemovalDialog: () -> Unit,
    text: String,
    onConfirmRemoveAction: () -> Unit,
    onRemovalDialogClosed: () -> Unit,
    onErrorModalStateClosed: () -> Unit,
) {
    MyErrorDialogProxy(
        errorModalState = removeSingleExpenseUiState.errorModalState,
        onErrorModalClose = onErrorModalStateClosed
    )

    YesOrNoDialog(
        openDialog = removeSingleExpenseUiState.isRemovalDialogVisible,
        onDialogClosed = onRemovalDialogClosed,
        content = { Text(text = text) },
        onConfirm = onConfirmRemoveAction,
        onCancel = onRemovalDialogClosed,
    )

    IconButton(
        onClick = onShowRemovalDialog
    ) {
        Icon(
            Icons.Filled.DeleteForever,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RemoveSingleExpenseIconButtonStatelessDarkPreview() {
    SetContentOnDarkPreview {
        RemoveSingleExpenseIconButtonStateless(
            removeSingleExpenseUiState = RemoveSingleExpenseUiState(),
            onRemovalDialogClosed = { },
            text = "Na pewno chcesz usunąć?",
            onShowRemovalDialog = {},
            onConfirmRemoveAction = { },
            onErrorModalStateClosed = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RemoveSingleExpenseIconButtonStatelessLightPreview() {
    SetContentOnLightPreview {
        RemoveSingleExpenseIconButtonStateless(
            removeSingleExpenseUiState = RemoveSingleExpenseUiState(),
            onRemovalDialogClosed = { },
            text = "Na pewno chcesz usunąć?",
            onShowRemovalDialog = {},
            onConfirmRemoveAction = { },
            onErrorModalStateClosed = {},
        )
    }
}

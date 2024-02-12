package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.remove

import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeleteForever
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ButtonActions
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.MyErrorDialogProxy
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.showShortText

// TODO: to w sumie takie samo jak RemoveSingleExpenseIconButtonStateless
@Composable
fun RemoveSingleCategoryIconButton(
    removeSingleCategoryViewModel: RemoveSingleCategoryViewModel = hiltViewModel(),
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: CategoryQuickSummary,
) {
    val context = currentAppContext()
    var deleteButtonIsLoading by remember { mutableStateOf(false) }
    var deleteErrorState by remember { mutableStateOf<ErrorModalState>(ErrorModalState.NotVisible) }

    val categoryRemovedText = stringResource(R.string.categoryRemoved)

    val cannotRemoveCategoryWithExpensesText =
        stringResource(R.string.cannotRemoveCategoryWithExpenses)

    val onConfirmRemovalAction = {
        deleteButtonIsLoading = true
        removeSingleCategoryViewModel.removeCategoryById(
            categoryQuickSummary.categoryId,
            ButtonActions(
                onSuccessAction = {
                    deleteButtonIsLoading = false
                    showShortText(
                        context,
                        categoryRemovedText + " ${categoryQuickSummary.categoryName}"
                    )
                    categoryScreenActions.onRefreshScreenActions.invoke()
                },
                onErrorAction = {
                    deleteErrorState = ErrorModalState.Visible(it)
                    deleteButtonIsLoading = false
                }
            )
        )
    }

    MyErrorDialogProxy(
        errorModalState = deleteErrorState,
        onErrorModalClose = { deleteErrorState = ErrorModalState.NotVisible },
    )

    // Remove Confirmation Dialog
    var categoryRemoveConfirmationDialog by remember { mutableStateOf(false) }

    YesOrNoDialog(
        openDialog = categoryRemoveConfirmationDialog,
        onDialogClosed = {
            categoryRemoveConfirmationDialog = false
        },
        content = {
            Text(text = stringResource(R.string.reallyHardRemoveCategory))
        },
        onConfirm = {
            if (categoryQuickSummary.numberOfExpenses == 0L) {
                onConfirmRemovalAction()
            } else {
                showShortText(context, cannotRemoveCategoryWithExpensesText)
            }
        }
    )
    RemoveSingleExpenseIconButtonStateless(
        isDeleteLoading = deleteButtonIsLoading,
        onClickAction = {
            categoryRemoveConfirmationDialog = true
        }
    )
}

@Composable
fun RemoveSingleExpenseIconButtonStateless(
    isDeleteLoading: Boolean,
    onClickAction: () -> Unit,
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

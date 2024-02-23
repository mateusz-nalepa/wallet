package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.singleCategoryDetails.actions.remove

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
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryScreenActions
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.RemoveCategoryState

@Composable
fun RemoveSingleCategoryIconButton(
    categoryScreenActions: CategoryScreenActions,
    categoryQuickSummary: CategoryQuickSummary,
    removeCategoryState: RemoveCategoryState,
) {
    // Remove Confirmation Dialog
    YesOrNoDialog(
        openDialog = removeCategoryState.categoryRemoveConfirmationDialogIsVisible,
        onDialogClosed = {
            categoryScreenActions.onErrorModalClose.invoke()
        },
        content = {
            Text(text = stringResource(R.string.areYouSureYouWantToRemoveCategory))
        },
        onConfirm = {
            categoryScreenActions.onCategoryRemoveAction.invoke(categoryQuickSummary.categoryId)
            categoryScreenActions.onCategoryRemoveModalClose.invoke()
        },
        onCancel = {
            categoryScreenActions.onCategoryRemoveModalClose.invoke()
        }
    )
    IconButton(
        onClick = {
            categoryScreenActions.onCategoryRemoveModalOpen.invoke()
        }
    ) {
        Icon(
            Icons.Filled.DeleteForever,
            contentDescription = null,
            modifier = Modifier.size(24.dp),
        )
    }
}


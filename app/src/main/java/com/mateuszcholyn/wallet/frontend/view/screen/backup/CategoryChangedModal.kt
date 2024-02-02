package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog

@Composable
fun CategoryChangedModal(
    openDialog: MutableState<Boolean>,
    onKeepCategoryFromDatabase: () -> Unit,
    onUseCategoryFromBackup: () -> Unit,
) {

    YesOrNoDialog(
        message = "Kategoria się zmieniła!",
        confirmText = "Zachowaj kategorię z bazy",
        cancelText = "Zachowaj kategorię z kopii zapasowej",
        openDialog = openDialog,
        onConfirm = {
            onKeepCategoryFromDatabase.invoke()
        },
        onCancel = {
            onUseCategoryFromBackup.invoke()
        }
    )

}

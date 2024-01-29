package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.OnCategoryChangedInput
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog

@Composable
fun categoryChangedModal(): OnCategoryChangedModalLauncher {

    val openCategoryChangedDialog = remember { mutableStateOf(false) }
    var onConfirmAction: () -> Unit = {}
    var onCancelAction: () -> Unit = {}
    YesOrNoDialog(
        message = "Kategoria się zmieniła!",
        confirmText = "Zachowaj kategorię z bazy",
        cancelText = "Zachowaj kategorię z kopii zapasowej",
        openDialog = openCategoryChangedDialog,
        onConfirm = {
            onConfirmAction.invoke()
        },
        onCancel = {
            onCancelAction.invoke()
        }
    )

    return OnCategoryChangedModalLauncher { categoryChangedInput ->
        onConfirmAction = categoryChangedInput.keepCategoryFromDatabase
        onCancelAction = categoryChangedInput.useCategoryFromBackup
        openCategoryChangedDialog.value = true
    }
}

class OnCategoryChangedModalLauncher(
    private val launcher: (OnCategoryChangedInput) -> Unit,
) {
    fun open(onCategoryChangedInput: OnCategoryChangedInput) {
        launcher.invoke(onCategoryChangedInput)
    }
}

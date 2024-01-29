package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.view.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.impo.fileSelector
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.frontend.view.util.showLongText
import java.time.LocalDateTime


@Composable
fun BackupImport(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {
    val openCategoryChangedDialog = remember { mutableStateOf(false) }
    var onKeepCategoryFromDatabase: () -> Unit = {}
    var onUseCategoryFromBackup: () -> Unit = {}
    YesOrNoDialog(
        message = "Kategoria się zmieniła!",
        confirmText = "Zachowaj kategorię z bazy",
        cancelText = "Zachowaj kategorię z kopii zapasowej",
        openDialog = openCategoryChangedDialog,
        onConfirm = {
            onKeepCategoryFromDatabase.invoke()
        },
        onCancel = {
            onUseCategoryFromBackup.invoke()
        }
    )

    val openExpenseChangedDialog = remember { mutableStateOf(false) }
    var onKeepExpenseFromDatabase: () -> Unit = {}
    var onUseExpenseFromBackup: () -> Unit = {}
    YesOrNoDialog(
        message = "Wydatek się zmienił!",
        confirmText = "Zachowaj wydatek z bazy",
        cancelText = "Zachowaj wydatek z kopii zapasowej",
        openDialog = openExpenseChangedDialog,
        onConfirm = {
            onKeepExpenseFromDatabase.invoke()
        },
        onCancel = {
            onUseExpenseFromBackup.invoke()
        }
    )

    val fileSelector =
        fileSelector(
            onFileSelected = {
                backupScreenViewModel.importBackupV1JsonString(
                    fileWithBackupCopy = it,
                    onCategoryChangedAction = { categoryChangedInput ->
                        onKeepCategoryFromDatabase = categoryChangedInput.keepCategoryFromDatabase
                        onUseCategoryFromBackup = categoryChangedInput.useCategoryFromBackup
                        openCategoryChangedDialog.value = true
                    },
                    onExpanseChangedAction = { expanseChangedInput ->
                        onKeepExpenseFromDatabase = expanseChangedInput.keepExpenseFromDatabase
                        onUseExpenseFromBackup = expanseChangedInput.useExpenseFromBackup
                        openExpenseChangedDialog.value = true
                    }
                )
            }
        )

    Button(
        onClick = { fileSelector.launch() },
        modifier = defaultButtonModifier,
    ) {
        Text(text = "Importuj dane")
    }
}


@Composable
fun BackupExport(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {
    val fileExporter =
        fileExporter(
            fileName = "wallet-backup-${LocalDateTime.now()}.json",
            fileContent = backupScreenViewModel.createBackupV1JsonString(),
            title = "Eksport danych",
        )

    Button(
        onClick = {
            fileExporter.launch()
        },
        modifier = defaultButtonModifier,
    ) {
        Text("Eksportuj dane")
    }
}

// TODO: dopiero na tym ekranie daj: verifyPermissions?? o ile faktycznie potrzebne XD
@Composable
fun BackupDataScreen(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {

    val context = currentAppContext()
    val importState by remember { backupScreenViewModel.importState }

    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
        // TODO: obtestuj to jakoś?? zrób jakieś error handling? XD
        BackupImport()
        BackupExport()

        when (val importStateTemp = importState) {
            is ImportState.NotStarted -> {}
            is ImportState.Error -> showLongText(context, importStateTemp.errorMessage)
            // TODO: pokaż podsumowanie jakoś bardziej prawilnie XD
            is ImportState.Success ->
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = importStateTemp.importV1Summary.toString()
                )

            is ImportState.Loading ->
                Text(
                    modifier = Modifier.fillMaxHeight(),
                    text = "Loading"
                )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun DummyScreenPreview() {
    BackupDataScreen()
}

// usun rzeczy powiązane z tą klasą XD
data class SaveAllExpensesV2WithCategoriesV2Model(
    val categories: List<CategoryV2>,
    val expenses: List<ExpenseV2>,
)

package com.mateuszcholyn.wallet.frontend.view.screen.backup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.export.fileExporter
import com.mateuszcholyn.wallet.frontend.view.screen.backup.file.import.fileSelector
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import com.mateuszcholyn.wallet.frontend.view.util.showLongText
import java.time.LocalDateTime


@Composable
fun BackupImport(
    backupScreenViewModel: BackupScreenViewModel = hiltViewModel(),
) {
    val context = currentAppContext()

    val fileSelector =
        fileSelector(
            onFileSelected = {
                backupScreenViewModel.importBackupV1JsonString(
                    jsonString = it.bufferedReader().readText(),
                    onImportSuccessAction = {
                        showLongText(context, "Importowanie zakończone!")
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
fun BackupDataScreen() {
    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
        // TODO: obtestuj to jakoś?? zrób jakieś error handling? XD
        BackupImport()
        BackupExport()
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

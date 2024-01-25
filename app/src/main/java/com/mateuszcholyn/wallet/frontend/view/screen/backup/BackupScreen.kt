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
import com.mateuszcholyn.wallet.frontend.view.screen.backup.xddd.newFileSelector
import com.mateuszcholyn.wallet.frontend.view.screen.backup.xddd.onFileSelected
import com.mateuszcholyn.wallet.frontend.view.screen.backup.xddd.selectedFileReader
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.util.stream.Collectors


@Composable
fun BackupImport(
    backupDataModel: BackupDataModel = hiltViewModel(),
) {
    val context = currentAppContext()

    val onExistingFileSelected = onFileSelected { pickedUri ->
        val xdLista = mutableListOf<String>()
        context
            .contentResolver
            .openInputStream(pickedUri)
            ?.use { outputStream ->
                xdLista.addAll(

                    outputStream
                        .bufferedReader()
                        .lines()
                        .collect(Collectors.toList())
                )
            }

        xdLista
    }

    val selectedFileReader = selectedFileReader(onExistingFileSelected = onExistingFileSelected)

    Button(
        onClick = { selectedFileReader.readFile() },
        modifier = defaultButtonModifier
    ) {
        Text("Importuj dane")
    }
}

@Composable
fun BackupExport() {
    val appContext = currentAppContext()

    val onNewFileSaved = onFileSelected { pickedUri ->
        appContext
            .contentResolver
            .openOutputStream(pickedUri)
            ?.use { outputStream ->
                outputStream.write("123".toByteArray())
            }
    }

    val newFileSelector = newFileSelector(onNewFileSaved = onNewFileSaved)

    Button(
        onClick = { newFileSelector.saveContent() },
        modifier = defaultButtonModifier,
    ) {
        Text("Eksportuj dane")
    }
}

@Composable
fun BackupDataScreen() {
    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
        BackupImport()
        BackupExport()
    }

//        Text(
//            text = stringResource(R.string.dummyView),
//            fontWeight = FontWeight.Bold,
//            color = Color.White,
//            modifier = Modifier.align(Alignment.CenterHorizontally),
//            textAlign = TextAlign.Center,
//            fontSize = 25.sp
//        )
//
//        Button(
//            onClick = {
////                readBackupData(dummyViewModel.allBackendServices())
////                showLongText(appContext, "Importowanie zakończone!")
//                showLongText(appContext, "Dane są już zaimportowane!")
//            },
//        ) {
//            Text("Importuj dane")
//        }
//
//        Button(
//            onClick = {
////                val saveAllExpensesV2WithCategoriesV2Model =
////                    SaveAllExpensesV2WithCategoriesV2Model(
////                        categories = dummyViewModel.getAllCategories(),
////                        expenses = dummyViewModel.getAllExpenses(),
////                    )
////
////                saveAllExpensesToFile(
////                    ctx = appContext,
////                    saveAllExpensesV2WithCategoriesV2Model = saveAllExpensesV2WithCategoriesV2Model
////                )
//                // Tworzenie okna dialogowego
//
//
//                showLongText(appContext, "Zakonczono eksport danych!")
//            },
//        ) {
//            Text("Eksport danych")
//        }

}


@Preview(showBackground = true)
@Composable
fun DummyScreenPreview() {
    BackupDataScreen()
}

data class SaveAllExpensesV2WithCategoriesV2Model(
    val categories: List<CategoryV2>,
    val expenses: List<ExpenseV2>,
)

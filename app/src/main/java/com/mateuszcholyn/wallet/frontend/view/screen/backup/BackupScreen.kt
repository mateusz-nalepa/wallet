package com.mateuszcholyn.wallet.frontend.view.screen.backup

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.view.screen.backup.xddd.onFileSelected
import com.mateuszcholyn.wallet.frontend.view.screen.backup.xddd.selectedFileReader
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.defaultButtonModifier
import com.mateuszcholyn.wallet.frontend.view.util.defaultModifier
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
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

const val KOD = 1

@Composable
fun BackupImportV2(
    backupDataModel: BackupDataModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val filePickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->

            if (uri == null) {
                return@rememberLauncherForActivityResult
            }


            // Tutaj masz Uri pliku, który został wybrany przez użytkownika
            // Możesz go teraz zapisać lub użyć w dowolny inny sposób
            context.contentResolver.openInputStream(uri)?.use {
                val file = File(context.getExternalFilesDir("Imports"), "xddd.json")


                try {
                    val outputStream: OutputStream = FileOutputStream(file)
                    it.copyTo(outputStream)
                } catch (e: Exception) {
                    throw e
                }
            }


        }


    Button(
        onClick = { filePickerLauncher.launch("application/json") },
        modifier = defaultButtonModifier,
    ) {
        Text(text = "Wybierz plik")
    }
}


@Composable
fun BackupExport() {
//    val appContext = currentAppContext()
//
//    val onNewFileSaved = onFileSelected { pickedUri ->
//        appContext
//            .contentResolver
//            .openOutputStream(pickedUri)
//            ?.use { outputStream ->
//                outputStream.write("123".toByteArray())
//            }
//    }
//
//    val newFileSelector = newFileSelector(onNewFileSaved = onNewFileSaved)

    val context = LocalContext.current


    val fileName = "TwojPlik8.json"
//    val file = File("${getExternalStorageDirectory()}/Exports", fileName)
    val file = File(context.getExternalFilesDir("Exports"), fileName)


    val fileContents = "To jest tekst do zapisania."


    try {
        val outputStream: OutputStream = FileOutputStream(file)
        // tutaj zapisujesz swoje dane
        // na przykład, jeśli masz dane w postaci String:
        val data: String = "tutaj są moje dane"
        outputStream.write(data.toByteArray())

        outputStream.flush()
        outputStream.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }


//    val fileUri = Uri.fromFile(file)

    val fileUri = try {


        FileProvider.getUriForFile(
            context,
            "${context.packageName}.provider",
            file
        )
    } catch (e: Exception) {
        println(e)
        throw e
    }


    // todo: dodaj usuwanie
    val sendIntent: Intent = Intent().apply {
        action = Intent.ACTION_SEND
        putExtra(Intent.EXTRA_STREAM, fileUri)
        type = "application/json"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
    }
    val shareIntent = Intent.createChooser(sendIntent, fileName)


    val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            // Usuwanie pliku po udostępnieniu
            val isDeleted: Boolean = file.delete()

            if (isDeleted) {
                Log.d("File status", "File deleted successfully")
            } else {
                Log.d("File status", "Failed to delete the file")
            }

            // Wyrejestrowanie BroadcastReceiver
            context.unregisterReceiver(this)
        }
    }


    context.registerReceiver(
        receiver,
        IntentFilter(Intent.ACTION_SEND),
        Context.RECEIVER_EXPORTED,
    )

    Button(
        onClick = {
            context.startActivity(Intent.createChooser(shareIntent, "XDDDDD"))
//            file.delete()
        },
        modifier = defaultButtonModifier,
    ) {
        Text("Eksportuj dane")
    }


//    Button(
//        onClick = { newFileSelector.saveContent() },
//        modifier = defaultButtonModifier,
//    ) {
//        Text("Eksportuj dane")
//    }
}

@Composable
fun BackupDataScreen() {
    Column(
        modifier = defaultModifier.fillMaxHeight(),
    ) {
//        BackupImport()
        BackupImportV2()
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

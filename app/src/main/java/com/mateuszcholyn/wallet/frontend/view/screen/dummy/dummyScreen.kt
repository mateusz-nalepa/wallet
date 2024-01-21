package com.mateuszcholyn.wallet.frontend.view.screen.dummy

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.showLongText
import java.util.stream.Collectors

//import uploadBasic


suspend fun zapisywaniePliku(appContext: Context) {
//    uploadBasic(appContext)

}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun DummyScreen(
    dummyViewModel: DummyViewModel = hiltViewModel()
) {


    val appContext = currentAppContext()

    val saveFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val pickedUri = it.data?.data ?: return@rememberLauncherForActivityResult
            appContext
                .contentResolver
                .openOutputStream(pickedUri)
                ?.use { outputStream ->
                    outputStream.write("123".toByteArray())
                }
        }

    val openFileLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            val pickedUri = it.data?.data ?: return@rememberLauncherForActivityResult
            val xdLista = mutableListOf<String>()

            appContext
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


    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {


        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_CREATE_DOCUMENT)
                    .apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "application/txt"
                        putExtra(Intent.EXTRA_TITLE, "export.dat")
                    }
                saveFileLauncher.launch(intent)
            }
        ) {
            Text("XD Export")
        }
        Button(
            onClick = {
                val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    .apply {
                        addCategory(Intent.CATEGORY_OPENABLE)
                        type = "*/*"
                    }
                openFileLauncher.launch(intent)
            }
        ) {
            Text("XD Import")
        }
        Text(
            text = stringResource(R.string.dummyView),
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterHorizontally),
            textAlign = TextAlign.Center,
            fontSize = 25.sp
        )

        Button(
            onClick = {
//                readBackupData(dummyViewModel.allBackendServices())
//                showLongText(appContext, "Importowanie zakończone!")
                showLongText(appContext, "Dane są już zaimportowane!")
            },
        ) {
            Text("Importuj dane")
        }

        Button(
            onClick = {
//                val saveAllExpensesV2WithCategoriesV2Model =
//                    SaveAllExpensesV2WithCategoriesV2Model(
//                        categories = dummyViewModel.getAllCategories(),
//                        expenses = dummyViewModel.getAllExpenses(),
//                    )
//
//                saveAllExpensesToFile(
//                    ctx = appContext,
//                    saveAllExpensesV2WithCategoriesV2Model = saveAllExpensesV2WithCategoriesV2Model
//                )
                // Tworzenie okna dialogowego


                showLongText(appContext, "Zakonczono eksport danych!")
            },
        ) {
            Text("Eksport danych")
        }

    }

}


@Preview(showBackground = true)
@Composable
fun DummyScreenPreview() {
    DummyScreen()
}

data class SaveAllExpensesV2WithCategoriesV2Model(
    val categories: List<CategoryV2>,
    val expenses: List<ExpenseV2>,
)

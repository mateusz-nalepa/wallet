package com.mateuszcholyn.wallet.frontend.view.screen.dummy

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.read.readBackupData
import com.mateuszcholyn.wallet.frontend.infrastructure.backup.save.saveAllExpensesToFile
import com.mateuszcholyn.wallet.frontend.view.util.currentAppContext
import com.mateuszcholyn.wallet.frontend.view.util.showLongText


@Composable
fun DummyScreen(
    dummyViewModel: DummyViewModel = hiltViewModel()
) {
    val appContext = currentAppContext()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorResource(id = R.color.colorPrimaryDark))
            .wrapContentSize(Alignment.Center)
    ) {
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
                readBackupData(dummyViewModel.allBackendServices())
                showLongText(appContext, "Migracja zakończona!")
//                showLongText(appContext, "Juz robiles migracje wczesniej!")
            },
        ) {
            Text("Wykonaj Migracje")
        }

        Button(
            onClick = {
                val saveAllExpensesV2WithCategoriesV2Model =
                    SaveAllExpensesV2WithCategoriesV2Model(
                        categories = dummyViewModel.getAllCategories(),
                        expenses = dummyViewModel.getAllExpenses(),
                    )

                saveAllExpensesToFile(
                    ctx = appContext,
                    saveAllExpensesV2WithCategoriesV2Model = saveAllExpensesV2WithCategoriesV2Model
                )
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
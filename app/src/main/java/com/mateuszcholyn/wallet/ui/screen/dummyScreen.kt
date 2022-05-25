package com.mateuszcholyn.wallet.ui.screen

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
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.ui.util.showLongText
import com.mateuszcholyn.wallet.util.appContext.currentAppContext
import org.kodein.di.compose.rememberInstance

@Composable
fun DummyScreen() {

    val categoryService: CategoryService by rememberInstance()
    val expenseService: ExpenseService by rememberInstance()

//    val expenseService: ExpenseService by rememberInstance()
//
//    val allExpenses =
//            expenseService.getAll(
//                    ExpenseSearchCriteria(
//                            allCategories = true,
//                            beginDate = minDate,
//                            endDate = maxDate,
//                    )
//            )
//
//    saveToFile(
//            ctx = ApplicationContext.appContext,
//            activity = ApplicationContext.appActivity,
//            expenses = allExpenses
//    )
//

    val context = currentAppContext()

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
//                    odpalMigracje(expenseService, categoryService)
                    showLongText(context, "Juz robiles migracje wczesniej!")
                },
        ) {
            Text("Wykonaj Migracje")
        }
    }

}


@Preview(showBackground = true)
@Composable
fun DummyScreenPreview() {
    DummyScreen()
}
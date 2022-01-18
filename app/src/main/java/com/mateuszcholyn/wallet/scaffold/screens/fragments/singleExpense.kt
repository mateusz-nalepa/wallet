package com.mateuszcholyn.wallet.scaffold.screens.fragments

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.routeWithId
import com.mateuszcholyn.wallet.scaffold.screens.descriptionOrDefault
import com.mateuszcholyn.wallet.scaffold.util.YesOrNoDialog
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.previewDi
import com.mateuszcholyn.wallet.util.toHumanText
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import java.time.LocalDateTime

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowExpense(
        id: Int,
        expense: Expense,
        navController: NavHostController,
        refreshFunction: () -> Unit,
        initialDetailsAreVisible: Boolean = false,
) {
    val expenseService: ExpenseService by rememberInstance()

    var detailsAreVisible by remember { mutableStateOf(initialDetailsAreVisible) }
    ListItem(
            icon = {
                IconButton(onClick = {
                    detailsAreVisible = !detailsAreVisible
                }) {
                    Icon(
                            Icons.Filled.Menu,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp),
                    )
                }

            },
            text = { Text("${id + 1}. ${expense.category.name}") },
            trailing = { Text(expense.amount.asPrinteableAmount()) },
            modifier = defaultModifier.padding(0.dp),
    )

    if (detailsAreVisible) {
        Column(modifier = defaultModifier) {
            Row(modifier = defaultModifier) {
                OutlinedTextField(
                        value = expense.descriptionOrDefault(),
                        onValueChange = {},
                        label = { Text("Opis") },
                        modifier = defaultModifier,
                        singleLine = true,
                        readOnly = true,
                )
            }
            Row(modifier = defaultModifier) {
                OutlinedTextField(
                        value = expense.date.toHumanText(),
                        onValueChange = {},
                        label = { Text("Data") },
                        modifier = defaultModifier,
                        singleLine = true,
                        readOnly = true,

                        )
            }
            Row(modifier = defaultModifier) {
                Button(
                        onClick = {
                            navController.navigate(NavDrawerItem.AddOrEditExpense.routeWithId(expenseId = expense.id))
                        },
                        modifier = defaultButtonModifier.weight(1f),
                ) {
                    Text("Edytuj")
                }
                val openDialog = remember { mutableStateOf(false) }
                YesOrNoDialog(
                        openDialog = openDialog,
                        onConfirm = {
                            expenseService.hardRemove(expenseId = expense.id)
                            refreshFunction()
                            detailsAreVisible = false

                        }
                )
                Button(
                        onClick = {
                            openDialog.value = true
                        },
                        modifier = defaultButtonModifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)) {
                    Text("Usuń")
                }

            }
        }
    }

    Divider()

}


@Preview(showBackground = true)
@Composable
fun ShowExpensePreview() {
    withDI(di = previewDi()) {
        Column {
            ShowExpense(
                    id = 1,
                    expense = Expense(
                            id = 1L,
                            amount = 5.0,
                            date = LocalDateTime.now(),
                            description = "Opis do Wydatku",
                            category = Category(
                                    id = 1L,
                                    name = "XD",
                            ),
                    ),
                    navController = rememberNavController(),
                    refreshFunction = {},
                    initialDetailsAreVisible = true,
            )
        }
    }
}
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.routeWithId
import com.mateuszcholyn.wallet.scaffold.screens.descriptionOrDefault
import com.mateuszcholyn.wallet.scaffold.util.YesOrNoDialog
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.toHumanText
import org.kodein.di.compose.rememberInstance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowExpense(
        id: Int,
        expense: Expense,
        navController: NavHostController,
        refreshFunction: () -> Unit,
) {
    val expenseService: ExpenseService by rememberInstance()

    var detailsAreVisible by remember { mutableStateOf(false) }
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
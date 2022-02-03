package com.mateuszcholyn.wallet.scaffold.screens.fragments

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.routeWithId
import com.mateuszcholyn.wallet.scaffold.screens.descriptionOrDefault
import com.mateuszcholyn.wallet.scaffold.util.YesOrNoDialog
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.previewDi
import com.mateuszcholyn.wallet.util.toHumanText
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import java.math.BigDecimal
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
            text = { Text("${id + 1}. ${expense.category.name}") },
            trailing = {
                Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(text = expense.amount.asPrinteableAmount(), fontSize = 16.sp)
                    Icon(
                            Icons.Filled.Paid,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                    )
                }

            },
            modifier = defaultModifier.clickable {
                detailsAreVisible = !detailsAreVisible
            },
    )

    if (detailsAreVisible) {
        Column {
            Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
                Icon(
                        Icons.Filled.Description,
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                )
                Text(text = expense.descriptionOrDefault())
            }
            Row(
                    modifier = defaultModifier.padding(bottom = 0.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(horizontalArrangement = Arrangement.Start) {
                    Icon(
                            Icons.Filled.Event,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                    )
                    Text(text = expense.date.toHumanText())
                }

                Row(horizontalArrangement = Arrangement.End) {
                    IconButton(
                            onClick = {
                                navController.navigate(NavDrawerItem.AddOrEditExpense.routeWithId(expenseId = expense.idOrThrow()))
                            }
                    ) {
                        Icon(
                                Icons.Filled.Edit,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                        )
                    }
                    val openDialog = remember { mutableStateOf(false) }
                    YesOrNoDialog(
                            openDialog = openDialog,
                            onConfirm = {
                                expenseService.hardRemove(expenseId = expense.idOrThrow())
                                refreshFunction()
                                detailsAreVisible = false

                            }
                    )
                    IconButton(
                            onClick = {
                                openDialog.value = true
                            }
                    ) {
                        Icon(
                                Icons.Filled.DeleteForever,
                                contentDescription = null,
                                modifier = Modifier.size(24.dp),
                        )
                    }
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
        MaterialTheme {
            Column {
                ShowExpense(
                        id = 1,
                        expense = Expense(
                                id = 1L,
                                amount = BigDecimal("5"),
                                date = LocalDateTime.now(),
                                description = "Opis do WydasdatkuOpis do WydatkuOpis do Wydatku\nOpis do WydatkuOpis do WydatkuOpis do Wydatku\n",
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
}
package com.mateuszcholyn.wallet.ui.screen.summary

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.ui.composables.YesOrNoDialog
import com.mateuszcholyn.wallet.ui.skeleton.NavDrawerItem
import com.mateuszcholyn.wallet.ui.skeleton.routeWithId
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.dateutils.toHumanText
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ShowExpenseModel @Inject constructor(
    private val expenseService: ExpenseService,
) : ViewModel() {
    fun expenseService(): ExpenseService = expenseService
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ShowExpense(
    id: Int,
    expense: Expense,
    navController: NavHostController,
    refreshFunction: () -> Unit,
    initialDetailsAreVisible: Boolean = false,
    showExpenseModel: ShowExpenseModel = hiltViewModel()
) {
    val expenseService = showExpenseModel.expenseService()

    var detailsAreVisible by remember { mutableStateOf(initialDetailsAreVisible) }
    ListItem(
        text = { Text("${id + 1}. ${expense.category.name}") },
        trailing = {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(text = expense.amount.asPrintableAmount(), fontSize = 16.sp)
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
                Text(text = expense.descriptionOrDefault(stringResource(R.string.noDescription)))
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
                            navController.navigate(
                                NavDrawerItem.AddOrEditExpense.routeWithId(
                                    expenseId = expense.idOrThrow()
                                )
                            )
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


//@Preview(showBackground = true)
//@Composable
//fun ShowExpensePreview() {
//    withDI(di = previewDi()) {
//        MaterialTheme {
//            Column {
//                ShowExpense(
//                        id = 1,
//                        expense = Expense(
//                                id = 1L,
//                                amount = BigDecimal("5"),
//                                date = LocalDateTime.now(),
//                                description = "Opis do WydasdatkuOpis do WydatkuOpis do Wydatku\nOpis do WydatkuOpis do WydatkuOpis do Wydatku\n",
//                                category = Category(
//                                        id = 1L,
//                                        name = "XD",
//                                ),
//                        ),
//                        navController = rememberNavController(),
//                        refreshFunction = {},
//                        initialDetailsAreVisible = true,
//                )
//            }
//        }
//    }
//}
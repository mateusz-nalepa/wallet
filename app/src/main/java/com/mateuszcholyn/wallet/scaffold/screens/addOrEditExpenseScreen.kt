package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.util.ComposeDateTimePicker
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.kodein.di.compose.rememberInstance
import java.time.LocalDateTime


@ExperimentalMaterialApi
@Composable
fun NewAddOrEditExpenseScreen(navController: NavHostController, actualExpenseId: Long) {

    val expenseService: ExpenseService by rememberInstance()
    val categoryService: CategoryService by rememberInstance()

    val options = categoryService.getAllOrderByUsageDesc()

    var expanded by remember { mutableStateOf(false) }
    val datePickerDialogState = rememberMaterialDialogState()

    val expenseOrNull = if (actualExpenseId.isDummy()) null else expenseService.getById(actualExpenseId)

    var selectedCategory by remember { mutableStateOf(if (actualExpenseId.isDummy()) options.first() else expenseOrNull!!.category) }
    var amount by remember { mutableStateOf(if (actualExpenseId.isDummy()) "" else expenseOrNull!!.amount.asPrinteableAmount().toString()) }
    var description by remember { mutableStateOf(if (actualExpenseId.isDummy()) "" else expenseOrNull!!.description) }
    var dateText by remember { mutableStateOf(if (actualExpenseId.isDummy()) LocalDateTime.now().toHumanText() else expenseOrNull!!.date.toHumanText()) }

    ComposeDateTimePicker(
            dialogState = datePickerDialogState,
            value = dateText,
            onValueChange = { dateText = it },
    )


    Column(modifier = defaultModifier) {
// We want to react on tap/press on TextField to show menu
        ExposedDropdownMenuBox(
                modifier = defaultModifier,
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
        ) {
            TextField(
                    modifier = defaultModifier,
                    readOnly = true,
                    value = selectedCategory.name,
                    onValueChange = { },
                    label = { Text("Kategoria") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                    modifier = defaultModifier,
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                            modifier = defaultModifier,
                            onClick = {
                                selectedCategory = selectionOption
                                expanded = false
                            }
                    ) {
                        Text(
                                text = selectionOption.name,
                                modifier = defaultModifier,
                        )
                    }
                }
            }
        }
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    label = { Text("Kwota") },
                    modifier = defaultModifier,
                    singleLine = true,
            )
        }
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Opis") },
                    modifier = defaultModifier,
                    maxLines = 5,

                    )
        }
        Row(
                modifier = defaultModifier,
        ) {

            OutlinedTextField(
                    value = dateText,
                    onValueChange = { dateText = it },
                    label = { Text("Data") },
                    modifier = defaultModifier.clickable {
                        datePickerDialogState.show()
                    },
                    enabled = false,
            )
        }
        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        if (actualExpenseId != -1L) {
                            expenseService.updateExpense(Expense(
                                    id = actualExpenseId,
                                    amount = amount.toDouble(),
                                    description = description,
                                    category = selectedCategory,
                                    date = dateText.toLocalDateTime(),

                                    ))
                        } else {
                            expenseService.addExpense(Expense(
                                    amount = amount.toDouble(),
                                    description = description,
                                    category = selectedCategory,
                                    date = dateText.toLocalDateTime(),

                                    ))

                        }
                        navController.navigate(NavDrawerItem.SummaryScreen.route)
                    },
                    modifier = defaultButtonModifier,
            ) {
                if (actualExpenseId != -1L) {
                    Text("Edytuj wydatek")
                } else {
                    Text("Dodaj wydatek")
                }
            }
        }
    }
}


@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NewAddOrEditExpenseScreenPreview() {
    val navController = rememberNavController()

    NewAddOrEditExpenseScreen(navController = navController, 5)
}


fun Long.isDummy(): Boolean =
        this == -1L
package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.addoreditexpense.AddOrEditExpenseViewModel
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance


@ExperimentalMaterialApi
@Composable
fun NewAddOrEditExpenseScreen() {
    val addOrEditExpenseViewModel: AddOrEditExpenseViewModel by rememberInstance()
    val expenseService: ExpenseService by rememberInstance()
    val categoryService: CategoryService by rememberInstance()

    val scope = rememberCoroutineScope()

    val options = categoryService.getAllOrderByUsageDesc()


    var expanded by remember { mutableStateOf(false) }
    var selectedCategory by remember { mutableStateOf(options[0]) }
    var amount by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }


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
                        Text(text = selectionOption.name,
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
        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        scope.launch {
                            println(selectedCategory)
                            println(amount)
                            println(description)
                        }
                    },
                    modifier = defaultButtonModifier,
            ) {
                Text("Dodaj wydatek")
            }
        }



    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NewAddOrEditExpenseScreenPreview() {
    NewAddOrEditExpenseScreen()
}
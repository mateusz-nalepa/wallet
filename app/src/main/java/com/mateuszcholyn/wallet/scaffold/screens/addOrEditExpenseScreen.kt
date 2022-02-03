package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.util.*
import com.mateuszcholyn.wallet.util.asFormattedAmount
import com.mateuszcholyn.wallet.util.previewDi
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.kodein.di.compose.rememberInstance
import org.kodein.di.compose.withDI
import java.time.LocalDateTime

data class CategoryViewModel(
        override val name: String,
        val id: Long,
) : DropdownElement


fun Category.toCategoryViewModel(): CategoryViewModel =
        CategoryViewModel(
                id = requireNotNull(id) { "Category with name $name should have id" },
                name = name,
        )

fun CategoryViewModel.toCategory(): Category =
        Category(
                id = id,
                name = name,
        )

@Composable
fun NoCategoryPresentInfo(
        navController: NavHostController,
) {
    Column(
            modifier = defaultModifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text("Aby dodać wydatek, musisz wpierw dodać kategorię")
        Button(
                onClick = { navController.navigate(NavDrawerItem.Category.route) },
                modifier = defaultButtonModifier,
        ) {
            Text(text = "Dodaj pierwszą kategorię")
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewAddOrEditExpenseScreen(navController: NavHostController, actualExpenseId: Long) {
    val expenseService: ExpenseService by rememberInstance()
    val categoryService: CategoryService by rememberInstance()

    val categoryNameOptions = categoryService.getAllOrderByUsageDesc().map { it.toCategoryViewModel() }

    if (categoryNameOptions.isEmpty()) {
        NoCategoryPresentInfo(navController)
        return
    }

    val datePickerDialogState = rememberMaterialDialogState()

    val expenseOrNull = if (actualExpenseId.isNonEditable()) null else expenseService.getById(actualExpenseId)

    var selectedCategory by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) categoryNameOptions.first() else expenseOrNull!!.category.toCategoryViewModel()) }
    var amount by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) "" else expenseOrNull!!.amount.asFormattedAmount().toString()) }

    var description by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) "" else expenseOrNull!!.description) }
    var dateText by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) LocalDateTime.now().toHumanText() else expenseOrNull!!.date.toHumanText()) }


    val isFormValid by derivedStateOf {
        !amount.isAmountInValid()
    }

    ComposeDateTimePicker(
            dialogState = datePickerDialogState,
            value = dateText,
            onValueChange = { dateText = it },
    )
    val state = rememberScrollState()

    Column(modifier = defaultModifier.verticalScroll(state)) {
        WalletDropdown(
                dropdownName = "Kategoria",
                selectedElement = selectedCategory,
                availableElements = categoryNameOptions,
                onItemSelected = {
                    selectedCategory = it
                },
        )
        Row(modifier = defaultModifier) {
            ValidatedTextField(
                    textFieldLabel = "Kwota",
                    value = amount,
                    onValueChange = { amount = it },
                    isValueInValidFunction = { it.isAmountInValid() },
                    valueInvalidText = "Niepoprawna kwota",
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
                    enabled = isFormValid,
                    onClick = {
                        expenseService.saveExpense(
                                Expense(
                                        id = actualExpenseId,
                                        amount = amount.toBigDecimal(),
                                        description = description,
                                        category = selectedCategory.toCategory(),
                                        date = dateText.toLocalDateTime(),

                                        ))
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


@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewAddOrEditExpenseScreenPreviewForAddExpense() {
    withDI(di = previewDi()) {
        NewAddOrEditExpenseScreen(navController = rememberNavController(), -1L)
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewAddOrEditExpenseScreenPreviewForUpdateExpense() {
    withDI(di = previewDi()) {
        NewAddOrEditExpenseScreen(navController = rememberNavController(), 1L)
    }
}


fun Long.isNonEditable(): Boolean =
        this == -1L

fun Long.isEditable(): Boolean =
        !isNonEditable()

fun String.isAmountInValid(): Boolean =
        this.isBlank() || this.startsWith("-") || this.cannotConvertToDouble()

fun String.cannotConvertToDouble(): Boolean =
        !kotlin
                .runCatching {
                    this.toDouble()
                    true
                }
                .getOrDefault(false)

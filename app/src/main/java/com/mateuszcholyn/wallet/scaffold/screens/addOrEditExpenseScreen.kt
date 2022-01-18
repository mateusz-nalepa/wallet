package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.util.*
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.kodein.di.compose.rememberInstance
import java.time.LocalDateTime

data class CategoryViewModel(
        override val name: String,
        val id: Long,
) : DropdownElement


fun Category.toCategoryViewModel(): CategoryViewModel =
        CategoryViewModel(
                id = id,
                name = name,
        )

fun CategoryViewModel.toCategory(): Category =
        Category(
                id = id,
                name = name,
        )

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewAddOrEditExpenseScreen(navController: NavHostController, actualExpenseId: Long) {

    val expenseService: ExpenseService by rememberInstance()
    val categoryService: CategoryService by rememberInstance()

    val options = categoryService.getAllOrderByUsageDesc().map { it.toCategoryViewModel() }

    val datePickerDialogState = rememberMaterialDialogState()

    val expenseOrNull = if (actualExpenseId.isNonEditable()) null else expenseService.getById(actualExpenseId)

    var selectedCategory by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) options.first() else expenseOrNull!!.category.toCategoryViewModel()) }
    var amount by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) "" else expenseOrNull!!.amount.asPrinteableAmount()) }

//    val isAmountInValid by derivedStateOf {
//        amount.isBlank() || amount.startsWith("-")
//    }

    var isAmountInValid by remember { mutableStateOf(false) }

    var description by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) "" else expenseOrNull!!.description) }
    var dateText by remember { mutableStateOf(if (actualExpenseId.isNonEditable()) LocalDateTime.now().toHumanText() else expenseOrNull!!.date.toHumanText()) }

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
                availableElements = options,
                onItemSelected = {
                    selectedCategory = it
                },
        )
        Row(modifier = defaultModifier) {
            ValidatedTextField(
                    value = amount,
                    onValueChange = { amount = it },
                    isValueInValidFunction = { it.isAmountInValid() },
                    valueInvalidText = "Niepoprawna kwota",
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
                        expenseService.saveExpense(
                                Expense(
                                        id = actualExpenseId,
                                        amount = amount.toDouble(),
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
fun NewAddOrEditExpenseScreenPreview() {
    val navController = rememberNavController()

    NewAddOrEditExpenseScreen(navController = navController, 5)
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

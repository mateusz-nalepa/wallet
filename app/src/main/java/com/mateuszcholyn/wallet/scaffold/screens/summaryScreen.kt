package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.NavDrawerItem
import com.mateuszcholyn.wallet.scaffold.routeWithId
import com.mateuszcholyn.wallet.scaffold.util.YesOrNoDialog
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.summary.SortingData
import com.mateuszcholyn.wallet.util.ALL_CATEGORIES
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.view.QuickRangeV2
import org.kodein.di.compose.rememberInstance

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewSummaryScreen(navController: NavHostController) {

    var categoriesExpanded by remember { mutableStateOf(false) }

    val categoryService: CategoryService by rememberInstance()
    val expenseService: ExpenseService by rememberInstance()


    val availableCategories =
            listOf(Category(name = ALL_CATEGORIES)) + categoryService.getAllOrderByUsageDesc()
    var selectedCategory by remember { mutableStateOf(availableCategories.first()) }

    // QUICK RANGE
    var quickDataRangeExpanded by remember { mutableStateOf(false) }
    val availableQuickRangeDataV2 = QuickRangeV2.quickRanges()
    var selectedQuickRangeData by remember { mutableStateOf(availableQuickRangeDataV2.first()) }

//    SORTING TYPE
    /////////////////////////////////////
    var sortingExpanded by remember { mutableStateOf(false) }
    val availableSortElements = SortingData.sortingListBetter
    var selectedSort by remember { mutableStateOf(availableSortElements.first()) }

    // results
    var expensesList by remember { mutableStateOf(listOf<Expense>()) }
    var summaryResultText by remember { mutableStateOf("0 zł / 1 d = 0 zł/d") }

    var amountRangeStart by remember { mutableStateOf("0") }
    var amountRangeEnd by remember { mutableStateOf(Int.MAX_VALUE.toString()) }

    var advancedFiltersExpanded by remember { mutableStateOf(false) }

    fun getExpenseSearchCriteria(): ExpenseSearchCriteria {
        return ExpenseSearchCriteria(
                allCategories = selectedCategory.isAllCategories(),
                categoryName = selectedCategory.actualCategoryName(),
                beginDate = selectedQuickRangeData.beginDate,
                endDate = selectedQuickRangeData.endDate,
                sort = selectedSort.sort,
                fromAmount = amountRangeStart.toDoubleOrDefaultZero(),
                toAmount = amountRangeEnd.toDoubleOrDefaultZero(),
        )
    }

    fun showAverageAmount() {
        val result = expenseService.averageExpense(getExpenseSearchCriteria())

        summaryResultText =
                """
            ${result.wholeAmount.asPrinteableAmount()} zł / ${result.days} d = ${result.averageAmount.asPrinteableAmount()} zł/d
        """.trimIndent()
    }

    fun showHistory() {
        expensesList = expenseService.getAll(getExpenseSearchCriteria())
    }

    showHistory()
    showAverageAmount()

    Column(modifier = defaultModifier) {
        ExposedDropdownMenuBox(
                modifier = defaultModifier,
                expanded = categoriesExpanded,
                onExpandedChange = {
                    categoriesExpanded = !categoriesExpanded
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
                                expanded = categoriesExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                    modifier = defaultModifier,
                    expanded = categoriesExpanded,
                    onDismissRequest = {
                        categoriesExpanded = false
                    }
            ) {
                availableCategories.forEach { category ->
                    DropdownMenuItem(
                            modifier = defaultModifier,
                            onClick = {
                                selectedCategory = category
                                categoriesExpanded = false
                            }
                    ) {
                        Text(
                                text = category.name,
                                modifier = defaultModifier,
                        )
                    }
                }
            }
        }

        ExposedDropdownMenuBox(
                modifier = defaultModifier,
                expanded = quickDataRangeExpanded,
                onExpandedChange = {
                    quickDataRangeExpanded = !quickDataRangeExpanded
                }
        ) {
            TextField(
                    modifier = defaultModifier,
                    readOnly = true,
                    value = selectedQuickRangeData.name,
                    onValueChange = { },
                    label = { Text("Zakres") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = quickDataRangeExpanded
                        )
                    },
                    colors = ExposedDropdownMenuDefaults.textFieldColors()
            )
            ExposedDropdownMenu(
                    modifier = defaultModifier,
                    expanded = quickDataRangeExpanded,
                    onDismissRequest = {
                        quickDataRangeExpanded = false
                    }
            ) {
                availableQuickRangeDataV2.forEach { quickRange ->
                    DropdownMenuItem(
                            modifier = defaultModifier,
                            onClick = {
                                selectedQuickRangeData = quickRange
                                quickDataRangeExpanded = false
                            }
                    ) {
                        Text(
                                text = quickRange.name,
                                modifier = defaultModifier,
                        )
                    }
                }
            }
        }


        //////////////////////////////////////////////////////////////////////////


        Row(modifier = defaultModifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            ClickableText(
                    text = if (advancedFiltersExpanded) AnnotatedString("Ukryj filtry") else AnnotatedString("Pokaż filtry"),
                    onClick = {
                        advancedFiltersExpanded = !advancedFiltersExpanded
                    },
            )
        }
        if (advancedFiltersExpanded) {
            ExposedDropdownMenuBox(
                    modifier = defaultModifier,
                    expanded = sortingExpanded,
                    onExpandedChange = {
                        sortingExpanded = !sortingExpanded
                    }
            ) {
                TextField(
                        modifier = defaultModifier,
                        readOnly = true,
                        value = selectedSort.name,
                        onValueChange = { },
                        label = { Text("Sortowanie") },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                    expanded = sortingExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors()
                )
                ExposedDropdownMenu(
                        modifier = defaultModifier,
                        expanded = sortingExpanded,
                        onDismissRequest = {
                            sortingExpanded = false
                        }
                ) {
                    availableSortElements.forEach { sortElement ->
                        DropdownMenuItem(
                                modifier = defaultModifier,
                                onClick = {
                                    selectedSort = sortElement
                                    sortingExpanded = false
                                }
                        ) {
                            Text(
                                    text = sortElement.name,
                                    modifier = defaultModifier,
                            )
                        }
                    }
                }
            }
            Row(modifier = defaultModifier) {
                OutlinedTextField(
                        value = amountRangeStart,
                        onValueChange = { amountRangeStart = it },
                        label = { Text("Od zł") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = defaultModifier.weight(1f),
                        singleLine = true,
                )
                OutlinedTextField(
                        value = amountRangeEnd,
                        onValueChange = { amountRangeEnd = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Do zł") },
                        modifier = defaultModifier.weight(1f),
                        singleLine = true,
                )
            }
        }
        //////////////////////////////////////////////////////////////////////////
        Row(
                modifier = defaultModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                    textAlign = TextAlign.Center,
                    text = summaryResultText,
                    modifier = defaultModifier,
                    fontSize = 20.sp,
            )
        }
        Row(modifier = defaultModifier) {
            Divider()
        }
        Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Wydatki", modifier = defaultModifier.weight(1f))
            Text(text = "Ilość: ${expensesList.size}", modifier = defaultModifier.weight(1f))
        }
        Row(modifier = defaultModifier) {
            Divider()
        }
        LazyColumn(
                modifier =
                Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 4.dp),

                ) {
            itemsIndexed(items = expensesList) { id, expense ->
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
                        trailing = { Text(expense.amount.asPrinteableAmount().toString()) }
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
                                        showHistory()
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
        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Preview(showBackground = true)
@Composable
fun NewSummaryScreenPreview() {
    NewSummaryScreen(rememberNavController())
}

fun Category.isAllCategories(): Boolean =
        name == ALL_CATEGORIES

fun Category.actualCategoryName(): String? =
        if (isAllCategories()) null else name

fun Expense.descriptionOrDefault(): String =
        if (description == "") "Brak opisu" else description


fun String.toDoubleOrDefaultZero(): Double =
        kotlin.runCatching { this.toDouble() }
                .getOrDefault(0.0)
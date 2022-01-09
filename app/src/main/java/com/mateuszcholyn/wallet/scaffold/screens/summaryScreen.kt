package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.summary.SortingData
import com.mateuszcholyn.wallet.util.ALL_CATEGORIES
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.view.QuickRangeV2
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance

@ExperimentalMaterialApi
@Composable
fun NewSummaryScreen(navController: NavHostController) {
    val scope = rememberCoroutineScope()

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
    var numberOfExpenses by remember { mutableStateOf("Ilość wydatków: 0") }
    var summaryResultText by remember { mutableStateOf("0 zł / 1 d = 0 zł/d") }

    fun getExpenseSearchCriteria(): ExpenseSearchCriteria {
        println("allCategories: ${selectedCategory.isAllCategories()}")
        println("categoryName: ${selectedCategory.actualCategoryName()}")
        println("beginDate: ${selectedQuickRangeData.beginDate}")
        println("endDate: ${selectedQuickRangeData.endDate}")
        println("sort: $selectedSort")


        return ExpenseSearchCriteria(
                allCategories = selectedCategory.isAllCategories(),
                categoryName = selectedCategory.actualCategoryName(),
                beginDate = selectedQuickRangeData.beginDate,
                endDate = selectedQuickRangeData.endDate,
                sort = selectedSort.sort,
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
        expensesList =
                expenseService
                        .getAll(getExpenseSearchCriteria())
                        .also { numberOfExpenses = "Ilość wydatków: ${it.size}" }
//                        .map {
//                            SummaryAdapterModel(
//                                    it.id,
//                                    it.description,
//                                    it.date.toHumanText(),
//                                    it.amount.asPrinteableAmount().toString(),
//                                    it.category.name,
//                            )
//                        }
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
                    label = { Text("Zakres") },
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
        Row(
                modifier = defaultModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                    textAlign = TextAlign.Center,
                    text = numberOfExpenses,
                    modifier = defaultModifier,
                    fontSize = 20.sp,
            )
        }
        Row(modifier = defaultModifier) {
            Divider()
        }
        Row(modifier = defaultModifier) {
            Text("Wydatki")
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
                                        scope.launch {
                                            navController.navigate(NavDrawerItem.AddOrEditExpense.routeWithId(expenseId = expense.id))
                                        }
                                    },
                                    modifier = defaultButtonModifier.weight(1f),
                            ) {
                                Text("Edytuj")
                            }
                            val openDialog = remember { mutableStateOf(false) }
                            YesOrNoDialog(
                                    openDialog = openDialog,
                                    onConfirm = {
                                        scope.launch {
                                            expenseService.hardRemove(expenseId = expense.id)
                                            showHistory()
                                            detailsAreVisible = false
                                        }
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

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NewSummaryScreenPreview() {
    NewSummaryScreen(rememberNavController())
}


@Composable
fun YesOrNoDialog(openDialog: MutableState<Boolean>, onConfirm: () -> Unit) {
    if (openDialog.value) {
        AlertDialog(
                onDismissRequest = {
                    openDialog.value = false
                },
                title = {
                    Text(text = "Na pewno usunąć wydatek?")
                },
                buttons = {
                    Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = { openDialog.value = false }
                        ) {
                            Text("Anuluj")
                        }
                    }
                    Row(
                            modifier = Modifier.padding(all = 8.dp),
                            horizontalArrangement = Arrangement.Center
                    ) {
                        Button(
                                modifier = Modifier.fillMaxWidth(),
                                onClick = {
                                    openDialog.value = false
                                    onConfirm.invoke()
                                }
                        ) {
                            Text("Usuń")
                        }
                    }

                }
        )
    }
}

fun Category.isAllCategories(): Boolean =
        name == ALL_CATEGORIES

fun Category.actualCategoryName(): String? =
        if (isAllCategories()) null else name

fun Expense.descriptionOrDefault(): String =
        if (description == "") "Brak opisu" else description
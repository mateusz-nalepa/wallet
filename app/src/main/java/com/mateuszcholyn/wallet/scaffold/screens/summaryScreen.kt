package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.AverageExpenseResult
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.scaffold.screens.fragments.ExpensesList
import com.mateuszcholyn.wallet.scaffold.screens.fragments.GroupedExpenses
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.summary.GroupingData
import com.mateuszcholyn.wallet.ui.summary.SortingData
import com.mateuszcholyn.wallet.util.ALL_CATEGORIES
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.view.QuickRangeV2
import org.kodein.di.compose.rememberInstance

@ExperimentalFoundationApi
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
//    Grouping TYPE
    /////////////////////////////////////
    var groupingExpanded by remember { mutableStateOf(false) }
    val availableGroupElements = GroupingData.groupingListBetter
    var selectedGroupElement by remember { mutableStateOf(availableGroupElements.first()) }
    var isGroupingEnabled by remember { mutableStateOf(false) }

    // results
    var expensesList by remember { mutableStateOf(listOf<Expense>()) }

    var expensesListGrouped by remember { mutableStateOf(mapOf<String, List<Expense>>()) }

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
        summaryResultText = expenseService.averageExpense(getExpenseSearchCriteria()).asTextSummary()
    }

    fun showHistory() {
        expensesList = expenseService.getAll(getExpenseSearchCriteria())

        expensesListGrouped = expensesList.groupBy(selectedGroupElement.groupFunction)
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
            Checkbox(checked = advancedFiltersExpanded, onCheckedChange = {
                advancedFiltersExpanded = !advancedFiltersExpanded
            })
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


            ////##########################################################
            Row(modifier = defaultModifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                Row(modifier = Modifier.weight(4f)) {
                    ExposedDropdownMenuBox(
                            modifier = defaultModifier,
                            expanded = groupingExpanded,
                            onExpandedChange = {
                                groupingExpanded = !groupingExpanded
                            }
                    ) {
                        TextField(
                                modifier = defaultModifier,
                                readOnly = true,
                                value = selectedGroupElement.name,
                                onValueChange = { },
                                enabled = isGroupingEnabled,
                                label = { Text("Grupowanie") },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                            expanded = groupingExpanded
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors()
                        )
                        ExposedDropdownMenu(
                                modifier = defaultModifier,
                                expanded = groupingExpanded,
                                onDismissRequest = {
                                    groupingExpanded = false
                                }
                        ) {
                            availableGroupElements.forEach { groupElement ->
                                DropdownMenuItem(
                                        modifier = defaultModifier,
                                        onClick = {
                                            selectedGroupElement = groupElement
                                            groupingExpanded = false
                                        }
                                ) {
                                    Text(
                                            text = groupElement.name,
                                            modifier = defaultModifier,
                                    )
                                }
                            }
                        }
                    }
                }

                Row(modifier = Modifier.weight(3f), horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = isGroupingEnabled, onCheckedChange = {
                        isGroupingEnabled = !isGroupingEnabled
                    })
                    Text(text = "Grupuj")
                }
            }

            ////##########################################################

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
        Row(modifier = defaultModifier) {
            Divider()
        }
        Row(modifier = defaultModifier, horizontalArrangement = Arrangement.SpaceBetween) {
            Text(text = "Ilość: ${expensesList.size}", modifier = defaultModifier.weight(1f))
            Text(text = summaryResultText, modifier = defaultModifier.weight(2f))
        }
        Row(modifier = defaultModifier) {
            Divider()
        }

        if (isGroupingEnabled) {
            GroupedExpenses(
                    navController = navController,
                    refreshFunction = { showHistory() },
                    expensesListGrouped = expensesListGrouped,
            )
        } else {
            ExpensesList(
                    navController = navController,
                    refreshFunction = { showHistory() },
                    expensesList = expensesList,
            )
        }
    }

}

@ExperimentalFoundationApi
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

fun AverageExpenseResult.asTextSummary(): String =
        "${wholeAmount.asPrinteableAmount()} / $days d = ${averageAmount.asPrinteableAmount()}/d"
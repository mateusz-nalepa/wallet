package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.mateuszcholyn.wallet.scaffold.util.WalletDropdown
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.summary.GroupingData
import com.mateuszcholyn.wallet.ui.summary.SortingData
import com.mateuszcholyn.wallet.util.ALL_CATEGORIES
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.view.QuickRangeV2
import org.kodein.di.compose.rememberInstance


@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NewSummaryScreen(navController: NavHostController) {
    val categoryService: CategoryService by rememberInstance()
    val expenseService: ExpenseService by rememberInstance()


    val availableCategories =
            listOf(Category(name = ALL_CATEGORIES).toCategoryViewModel()) + categoryService.getAllOrderByUsageDesc().map { it.toCategoryViewModel() }
    var selectedCategory by remember { mutableStateOf(availableCategories.first()) }

    // QUICK RANGE
    val availableQuickRangeDataV2 = QuickRangeV2.quickRanges()
    var selectedQuickRangeData by remember { mutableStateOf(availableQuickRangeDataV2.first()) }

//    SORTING TYPE
    /////////////////////////////////////
    val availableSortElements = SortingData.sortingListBetter
    var selectedSort by remember { mutableStateOf(availableSortElements.first()) }
//    Grouping TYPE
    /////////////////////////////////////
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
                categoryId = selectedCategory.actualCategoryId(),
                beginDate = selectedQuickRangeData.beginDate,
                endDate = selectedQuickRangeData.endDate,
                sort = selectedSort.sort,
                fromAmount = amountRangeStart.toDoubleOrDefaultZero(),
                toAmount = amountRangeEnd.toDoubleOrDefaultZero(),
        )
    }

    fun refreshScreen() {
        val summaryResult = expenseService.getSummary(getExpenseSearchCriteria())

        expensesList = summaryResult.expenses
        expensesListGrouped = expensesList.groupBy(selectedGroupElement.groupFunction)

        summaryResultText = summaryResult.averageExpenseResult.asTextSummary()
    }

    refreshScreen()

    Column(modifier = defaultModifier) {

        WalletDropdown(
                dropdownName = "Kategoria",
                selectedElement = selectedCategory,
                availableElements = availableCategories,
                onItemSelected = {
                    selectedCategory = it
                },
        )

        WalletDropdown(
                dropdownName = "Zakres",
                selectedElement = selectedQuickRangeData,
                availableElements = availableQuickRangeDataV2,
                onItemSelected = {
                    selectedQuickRangeData = it
                },
        )

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
            WalletDropdown(
                    dropdownName = "Sortowanie",
                    selectedElement = selectedSort,
                    onItemSelected = { selectedSort = it },
                    availableElements = availableSortElements,
            )

            ////##########################################################
            Row(modifier = defaultModifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                Row(modifier = Modifier.weight(4f)) {
                    WalletDropdown(
                            dropdownName = "Grupowanie",
                            selectedElement = selectedGroupElement,
                            availableElements = availableGroupElements,
                            onItemSelected = {
                                selectedGroupElement = it
                            },
                            isEnabled = isGroupingEnabled,
                    )
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
        Divider()
        Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
            Text(text = "Ilość: ${expensesList.size}", modifier = defaultModifier.weight(1f))
        }
        Row(modifier = defaultModifier.padding(top = 0.dp)) {
            Text(text = summaryResultText, modifier = defaultModifier.weight(2f))
        }
        Divider()

        if (isGroupingEnabled) {
            GroupedExpenses(
                    navController = navController,
                    refreshFunction = { refreshScreen() },
                    expensesListGrouped = expensesListGrouped,
                    groupNameFunction = selectedGroupElement.groupFunctionName,
            )
        } else {
            ExpensesList(
                    navController = navController,
                    refreshFunction = { refreshScreen() },
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

fun CategoryViewModel.isAllCategories(): Boolean =
        name == ALL_CATEGORIES

fun CategoryViewModel.actualCategoryId(): Long? =
        if (isAllCategories()) null else id

fun Expense.descriptionOrDefault(): String =
        if (description == "") "Brak opisu" else description


fun String.toDoubleOrDefaultZero(): Double =
        kotlin.runCatching { this.toDouble() }
                .getOrDefault(0.0)

fun AverageExpenseResult.asTextSummary(): String =
        "${wholeAmount.asPrinteableAmount()} / $days d = ${averageAmount.asPrinteableAmount()}/d"
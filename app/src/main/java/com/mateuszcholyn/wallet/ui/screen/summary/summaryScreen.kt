package com.mateuszcholyn.wallet.ui.screen.summary

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.AverageExpenseResult
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.ui.dropdown.WalletDropdown
import com.mateuszcholyn.wallet.ui.dropdown.groupingDataXD
import com.mateuszcholyn.wallet.ui.dropdown.quickRanges
import com.mateuszcholyn.wallet.ui.dropdown.sortingElements
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.CategoryViewModel
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.toCategoryViewModel
import com.mateuszcholyn.wallet.ui.util.defaultModifier
import com.mateuszcholyn.wallet.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.toDoubleOrDefaultZero
import org.kodein.di.compose.rememberInstance

const val ALL_CATEGORIES = "Wszystkie kategorie"

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun NewSummaryScreen(navController: NavHostController) {
    val categoryService: CategoryService by rememberInstance()
    val expenseService: ExpenseService by rememberInstance()


    val availableCategories =
            listOf(CategoryViewModel(name = ALL_CATEGORIES, isAllCategories = true)) + categoryService.getAllOrderByUsageDesc().map { it.toCategoryViewModel() }
    var selectedCategory by remember { mutableStateOf(availableCategories.first()) }

    // QUICK RANGE
    val availableQuickRangeDataV2 = quickRanges()
    var selectedQuickRangeData by remember { mutableStateOf(availableQuickRangeDataV2.first()) }

//    SORTING TYPE
    /////////////////////////////////////
    val availableSortElements = sortingElements()
    var selectedSort by remember { mutableStateOf(availableSortElements.first()) }
//    Grouping TYPE
    /////////////////////////////////////
    val availableGroupElements = groupingDataXD()
    var selectedGroupElement by remember { mutableStateOf(availableGroupElements.first()) }
    var isGroupingEnabled by remember { mutableStateOf(false) }

    // results
    var expensesList by remember { mutableStateOf(listOf<Expense>()) }

    var expensesListGrouped by remember { mutableStateOf(mapOf<String, List<Expense>>()) }

    val defaultSummaryResultText = stringResource(R.string.defaultSummaryResultText)

    var summaryResultText by remember { mutableStateOf(defaultSummaryResultText) }

    var amountRangeStart by remember { mutableStateOf(0.toString()) }
    var amountRangeEnd by remember { mutableStateOf(Int.MAX_VALUE.toString()) }

    var advancedFiltersExpanded by remember { mutableStateOf(false) }

    fun getExpenseSearchCriteria(): ExpenseSearchCriteria {
        return ExpenseSearchCriteria(
                allCategories = selectedCategory.isAllCategories,
                categoryId = selectedCategory.actualCategoryId(),
                beginDate = selectedQuickRangeData.beginDate,
                endDate = selectedQuickRangeData.endDate,
                sort = selectedSort.sort,
                isAllExpenses = selectedQuickRangeData.isAllExpenses,
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
                dropdownName = stringResource(R.string.category),
                selectedElement = selectedCategory,
                availableElements = availableCategories,
                onItemSelected = {
                    selectedCategory = it
                },
        )

        WalletDropdown(
                dropdownName = stringResource(R.string.range),
                selectedElement = selectedQuickRangeData,
                availableElements = availableQuickRangeDataV2,
                onItemSelected = {
                    selectedQuickRangeData = it
                },
        )

        Row(modifier = defaultModifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {
            ClickableText(
                    text = if (advancedFiltersExpanded) AnnotatedString(stringResource(R.string.hideFilters)) else AnnotatedString(stringResource(R.string.showFilters)),
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
                    dropdownName = stringResource(R.string.Sorting),
                    selectedElement = selectedSort,
                    onItemSelected = { selectedSort = it },
                    availableElements = availableSortElements,
            )

            ////##########################################################
            Row(modifier = defaultModifier, horizontalArrangement = Arrangement.Center, verticalAlignment = Alignment.CenterVertically) {

                Row(modifier = Modifier.weight(4f)) {
                    WalletDropdown(
                            dropdownName = stringResource(R.string.grouping),
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
                    Text(text = stringResource(R.string.group))
                }
            }

            ////##########################################################

            Row(modifier = defaultModifier) {
                OutlinedTextField(
                        value = amountRangeStart,
                        onValueChange = { amountRangeStart = it },
                        label = { Text(stringResource(R.string.amountFrom)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = defaultModifier.weight(1f),
                        singleLine = true,
                )
                OutlinedTextField(
                        value = amountRangeEnd,
                        onValueChange = { amountRangeEnd = it },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text(stringResource(R.string.amountTo)) },
                        modifier = defaultModifier.weight(1f),
                        singleLine = true,
                )
            }
        }
        Divider()
        Row(modifier = defaultModifier.padding(bottom = 0.dp)) {
            Text(text = stringResource(R.string.quantity) + " ${expensesList.size}", modifier = defaultModifier.weight(1f))
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

fun CategoryViewModel.actualCategoryId(): Long? =
        if (isAllCategories) null else id

fun Expense.descriptionOrDefault(defaultDescription: String): String =
        if (description == "") defaultDescription else description

fun AverageExpenseResult.asTextSummary(): String =
        "${wholeAmount.asPrintableAmount()} / $days d = ${averageAmount.asPrintableAmount()}/d"
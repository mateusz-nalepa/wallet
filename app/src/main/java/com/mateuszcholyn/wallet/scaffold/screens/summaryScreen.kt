package com.mateuszcholyn.wallet.scaffold.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.scaffold.util.defaultButtonModifier
import com.mateuszcholyn.wallet.scaffold.util.defaultModifier
import com.mateuszcholyn.wallet.ui.summary.SortingData
import com.mateuszcholyn.wallet.util.ALL_CATEGORIES
import com.mateuszcholyn.wallet.view.QuickRangeV2
import kotlinx.coroutines.launch
import org.kodein.di.compose.rememberInstance

@ExperimentalMaterialApi
@Composable
fun NewSummaryScreen() {
    val scope = rememberCoroutineScope()

    var categoriesExpanded by remember { mutableStateOf(false) }

    val categoryService: CategoryService by rememberInstance()
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

        Row(modifier = defaultModifier) {
            Button(
                    onClick = {
                        scope.launch {
//                            ExpenseSearchCriteria(
//                                    allCategories = isAllCategories(),
//                                    categoryName = if (getActualCategoryName() == ALL_CATEGORIES) null else getActualCategoryName(),
//                                    beginDate = beginDate.value!!.toLocalDateTime(),
//                                    endDate = endDate.value!!.toLocalDateTime(),
//                                    fromAmount = fromAmount,
//                                    toAmount = toAmount,
//                                    sort = SortingData.getSortByIndexName(actualSortPosition),
//                            )

                            println("allCategories: ${selectedCategory.isAllCategories()}")
                            println("categoryName: ${selectedCategory.actualCategoryName()}")
                            println("beginDate: ${selectedQuickRangeData.beginDate}")
                            println("endDate: ${selectedQuickRangeData.endDate}")
                            println("sort: $selectedSort")
                        }
                    },
                    modifier = defaultButtonModifier,
            ) {
                Text("Pokaż Podsumowanie")
            }
        }

    }

}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun NewSummaryScreenPreview() {
    NewSummaryScreen()
}



fun Category.isAllCategories() : Boolean =
        name == ALL_CATEGORIES

fun Category.actualCategoryName() : String? =
        if (isAllCategories()) null else name
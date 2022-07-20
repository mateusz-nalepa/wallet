package com.mateuszcholyn.wallet.ui.screen.summary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.AverageExpenseResult
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.ui.dropdown.*
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.CategoryViewModelForAddOrEditExpense
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.toCategoryViewModel
import com.mateuszcholyn.wallet.util.EMPTY_STRING
import com.mateuszcholyn.wallet.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.toDoubleOrDefaultZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class SummaryState {
    object Loading : SummaryState()
    data class Success(val summarySuccessContent: SummarySuccessContent) : SummaryState()
    data class Error(val errorMessage: String) : SummaryState()
}

data class SummarySuccessContent(
    val expensesList: List<Expense>,
    val expensesGrouped: Map<String, List<Expense>>,
    val summaryResultText: String,
)

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val categoryService: CategoryService,
    private val expenseService: ExpenseService,
) : ViewModel() {


    fun initScreen() {
        refreshScreen()
    }

    private var _availableCategories =
        mutableListOf(*readCategoriesList().toTypedArray()).toMutableStateList()
    val availableCategories: List<CategoryViewModelForAddOrEditExpense>
        get() = _availableCategories

    private var _selectedCategory = mutableStateOf(availableCategories.first())
    val selectedCategory: CategoryViewModelForAddOrEditExpense
        get() = _selectedCategory.value

    // quick range
    private var _availableQuickRangeDataV2 =
        mutableListOf(*quickRanges().toTypedArray()).toMutableStateList()
    val availableQuickRangeDataV2: List<QuickRangeDataV2>
        get() = _availableQuickRangeDataV2

    private var _selectedQuickRangeData = mutableStateOf(_availableQuickRangeDataV2.first())
    val selectedQuickRangeData: QuickRangeDataV2
        get() = _selectedQuickRangeData.value

    // sortElement
    private var _availableSortingElements =
        mutableListOf(*sortingElements().toTypedArray()).toMutableStateList()
    val availableSortingElements: List<SortElement>
        get() = _availableSortingElements

    private var _selectedSortElement = mutableStateOf(_availableSortingElements.first())
    val selectedSortElement: SortElement
        get() = _selectedSortElement.value

    // is groupingEnabled
    private val _isGroupingEnabled = mutableStateOf(false)
    val isGroupingEnabled: Boolean
        get() = _isGroupingEnabled.value

    // grouping dropdown
    private var _availableGroupElements =
        mutableListOf(*groupingDataXD().toTypedArray()).toMutableStateList()
    val availableGroupElements: List<GroupElement>
        get() = _availableGroupElements

    private var _selectedGroupElement = mutableStateOf(_availableGroupElements.first())
    val selectedGroupElement: GroupElement
        get() = _selectedGroupElement.value

    // amount range
    private val _amountRangeStart = mutableStateOf(0.toString())
    val amountRangeStart: String
        get() = _amountRangeStart.value

    private val _amountRangeEnd = mutableStateOf(Int.MAX_VALUE.toString())
    val amountRangeEnd: String
        get() = _amountRangeEnd.value

    // Results
    private var _summaryState: MutableState<SummaryState> = mutableStateOf(SummaryState.Loading)
    val summaryState: State<SummaryState>
        get() = _summaryState

    private fun readCategoriesList(): List<CategoryViewModelForAddOrEditExpense> {
        return listOf(
            CategoryViewModelForAddOrEditExpense(
                name = "Wszystkie kategorie", // TODO: move to screen... somehow :D
                isAllCategories = true
            )
        ) + categoryService.getAllWithDetailsOrderByUsageDesc().map { it.toCategoryViewModel() }
    }

    fun expenseService(): ExpenseService = expenseService
    fun categoryService(): CategoryService = categoryService
    fun updateSelectedCategory(newSelectedCategory: CategoryViewModelForAddOrEditExpense) {
        _selectedCategory.value = newSelectedCategory
    }

    fun updateQuickRangeData(quickRangeDataV2: QuickRangeDataV2) {
        _selectedQuickRangeData.value = quickRangeDataV2
    }

    fun updateSortElement(sortElement: SortElement) {
        _selectedSortElement.value = sortElement
    }

    fun groupingCheckBoxChecked(newValue: Boolean) {
        _isGroupingEnabled.value = newValue
    }

    fun updateGroupElement(groupElement: GroupElement) {
        _selectedGroupElement.value = groupElement
    }

    fun updateAmountRangeStart(newAmountRangeStart: String) {
        _amountRangeStart.value = newAmountRangeStart
    }

    fun updateAmountRangeEnd(newAmountRangeEnd: String) {
        _amountRangeEnd.value = newAmountRangeEnd
    }

    fun refreshScreen() {
        viewModelScope.launch {
            try {
                _summaryState.value = SummaryState.Loading
                _summaryState.value = SummaryState.Success(prepareSummarySuccessContent())
            } catch (e: Exception) {
                Log.d("BK", "Exception: ${e.message}")
                _summaryState.value = SummaryState.Error(e.message ?: "Unknown error sad times")
            }
        }
    }

    private fun prepareSummarySuccessContent(): SummarySuccessContent {
        val summaryResult = expenseService.getSummary(getExpenseSearchCriteria())
        return SummarySuccessContent(
            expensesList = summaryResult.expenses,
            expensesGrouped = summaryResult.expenses.groupBy(selectedGroupElement.groupFunction),
            summaryResultText = summaryResult.averageExpenseResult.asTextSummary(),
        )
    }

    private fun getExpenseSearchCriteria(): ExpenseSearchCriteria {
        return ExpenseSearchCriteria(
            allCategories = selectedCategory.isAllCategories,
            categoryId = selectedCategory.actualCategoryId(),
            beginDate = selectedQuickRangeData.beginDate,
            endDate = selectedQuickRangeData.endDate,
            sort = selectedSortElement.sort,
            isAllExpenses = selectedQuickRangeData.isAllExpenses,
            fromAmount = amountRangeStart.toDoubleOrDefaultZero(),
            toAmount = amountRangeEnd.toDoubleOrDefaultZero(),
        )
    }


}


fun CategoryViewModelForAddOrEditExpense.actualCategoryId(): Long? =
    if (isAllCategories) null else id

fun Expense.descriptionOrDefault(defaultDescription: String): String =
    if (description == EMPTY_STRING) defaultDescription else description

fun AverageExpenseResult.asTextSummary(): String =
    "${wholeAmount.asPrintableAmount()} / $days d = ${averageAmount.asPrintableAmount()}/d"
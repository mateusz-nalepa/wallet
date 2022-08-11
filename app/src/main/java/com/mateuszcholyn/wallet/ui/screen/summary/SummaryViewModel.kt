package com.mateuszcholyn.wallet.ui.screen.summary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.AverageExpenseResult
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.ui.dropdown.*
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.CategoryView
import com.mateuszcholyn.wallet.ui.screen.addoreditexpense.toCategoryView
import com.mateuszcholyn.wallet.util.EMPTY_STRING
import com.mateuszcholyn.wallet.util.asPrintableAmount
import com.mateuszcholyn.wallet.util.toDoubleOrDefaultZero
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

fun initialCategory(): CategoryView {
    return CategoryView(
        name = "Wszystkie kategorie",
        nameKey = R.string.summaryScreen_allCategories,
        isAllCategories = true
    )
}

data class SummarySearchForm(
    val selectedCategory: CategoryView = initialCategory(),
    val selectedQuickRangeData: QuickRangeDataV2 = quickRanges().first(),
    val selectedSortElement: SortElement = sortingElements().first(),
    val amountRangeStart: String = 0.toString(),
    val amountRangeEnd: String = Int.MAX_VALUE.toString(),
    //
    val isGroupingEnabled: Boolean = false,
    val selectedGroupElement: GroupElement = groupingDataXD().first(),
)

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
    private val searchServiceAPI: SearchServiceAPI,
) : ViewModel() {

    fun initScreen() {
        refreshScreen()
    }

    private val _searchForm = mutableStateOf(SummarySearchForm())
    val summarySearchForm: SummarySearchForm
        get() = _searchForm.value

    private var _summaryState: MutableState<SummaryState> = mutableStateOf(SummaryState.Loading)
    val summaryState: State<SummaryState>
        get() = _summaryState

    fun readCategoriesList(): List<CategoryView> =
        listOf(initialCategory()) + categoryService.getAllWithDetailsOrderByUsageDesc()
            .map { it.toCategoryView() }

    fun updateSelectedCategory(newSelectedCategory: CategoryView) {
        _searchForm.value = _searchForm.value.copy(selectedCategory = newSelectedCategory)
        refreshScreen()
    }

    fun updateQuickRangeData(newQuickRangeDataV2: QuickRangeDataV2) {
        _searchForm.value = _searchForm.value.copy(selectedQuickRangeData = newQuickRangeDataV2)
        refreshScreen()
    }

    fun updateSortElement(newSortElement: SortElement) {
        _searchForm.value = _searchForm.value.copy(selectedSortElement = newSortElement)
        refreshScreen()
    }

    fun groupingCheckBoxChecked(newValue: Boolean) {
        _searchForm.value = _searchForm.value.copy(isGroupingEnabled = newValue)
        refreshScreen()
    }

    fun updateGroupElement(groupElement: GroupElement) {
        _searchForm.value = _searchForm.value.copy(selectedGroupElement = groupElement)
        refreshScreen()
    }

    fun updateAmountRangeStart(newAmountRangeStart: String) {
        _searchForm.value = _searchForm.value.copy(amountRangeStart = newAmountRangeStart)
        refreshScreen()
    }

    fun updateAmountRangeEnd(newAmountRangeEnd: String) {
        _searchForm.value = _searchForm.value.copy(amountRangeEnd = newAmountRangeEnd)
        refreshScreen()
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
        val summaryResult = expenseService.getSummary(summarySearchForm.toExpenseSearchCriteria())
        return SummarySuccessContent(
            expensesList = summaryResult.expenses,
            expensesGrouped = summaryResult.expenses.groupBy(summarySearchForm.selectedGroupElement.groupFunction),
            summaryResultText = summaryResult.averageExpenseResult.asTextSummary(),
        )
    }

}

private fun SummarySearchForm.toExpenseSearchCriteria(): ExpenseSearchCriteria =
    ExpenseSearchCriteria(
        allCategories = selectedCategory.isAllCategories,
        categoryId = selectedCategory.actualCategoryId(),
        beginDate = selectedQuickRangeData.beginDate,
        endDate = selectedQuickRangeData.endDate,
        sort = selectedSortElement.sort,
        isAllExpenses = selectedQuickRangeData.isAllExpenses,
        fromAmount = amountRangeStart.toDoubleOrDefaultZero(),
        toAmount = amountRangeEnd.toDoubleOrDefaultZero(),
    )

fun CategoryView.actualCategoryId(): Long? =
    if (isAllCategories) null else id

fun Expense.descriptionOrDefault(defaultDescription: String): String =
    if (description == EMPTY_STRING) defaultDescription else description

fun AverageExpenseResult.asTextSummary(): String =
    "${wholeAmount.asPrintableAmount()} / $days d = ${averageAmount.asPrintableAmount()}/d"
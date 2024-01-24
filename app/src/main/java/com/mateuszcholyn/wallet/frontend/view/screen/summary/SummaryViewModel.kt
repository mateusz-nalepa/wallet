package com.mateuszcholyn.wallet.frontend.view.screen.summary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchAverageExpenseResult
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.domain.appstate.AppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.dropdown.*
import com.mateuszcholyn.wallet.frontend.view.screen.addoreditexpense.CategoryView
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import com.mateuszcholyn.wallet.frontend.view.util.toDoubleOrDefaultZero
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

fun initialCategory(): CategoryView =
    CategoryView(
        name = "Wszystkie kategorie",
        nameKey = R.string.summaryScreen_allCategories,
        categoryId = null,
    )

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
    val expensesList: List<SearchSingleResult>,
    val expensesGrouped: Map<String, List<SearchSingleResult>>,
    val summaryResultText: String,
)

@HiltViewModel
class SummaryViewModel @Inject constructor(
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    private val searchServiceUseCase: SearchServiceUseCase,
    private val appIsConfigured: AppIsConfigured,
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
        listOf(initialCategory()) +
                getCategoriesQuickSummaryUseCase.invoke().quickSummaries.map { it.toCategoryView() }

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
        val summaryResult = searchServiceUseCase.invoke(summarySearchForm.toSearchCriteria())
        val expenses = summaryResult.expenses

        return SummarySuccessContent(
            expensesList = expenses,
            expensesGrouped = expenses.groupBy(summarySearchForm.selectedGroupElement.groupFunction),
            summaryResultText = summaryResult.averageExpenseResult.asTextSummary(),
        )
    }

}

private fun SummarySearchForm.toSearchCriteria(): SearchCriteria =
    SearchCriteria(
        categoryId = selectedCategory.categoryId?.let { CategoryId(it) },
        beginDate = selectedQuickRangeData.beginDate?.fromUserLocalTimeZoneToUTCInstant(),
        endDate = selectedQuickRangeData.endDate?.fromUserLocalTimeZoneToUTCInstant(),
        fromAmount = amountRangeStart.toDoubleOrDefaultZero().toBigDecimal(),
        toAmount = amountRangeEnd.toDoubleOrDefaultZero().toBigDecimal(),
        sort = selectedSortElement.sort,
    )

fun SearchSingleResult.descriptionOrDefault(defaultDescription: String): String =
    if (description == EMPTY_STRING) defaultDescription else description


fun SearchAverageExpenseResult.asTextSummary(): String =
    "${wholeAmount.asPrintableAmount()} / $days d = ${averageAmount.asPrintableAmount()}/d"

fun CategoryQuickSummary.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = categoryName,
    )
package com.mateuszcholyn.wallet.frontend.view.screen.summary

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchAverageExpenseResult
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.domain.appstate.AppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.CategoryView
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

fun initialCategory(): CategoryView =
    CategoryView(
        name = "Wszystkie kategorie",
        nameKey = R.string.summaryScreen_allCategories,
        categoryId = null,
    )

sealed class SummaryResultState {
    data object Loading : SummaryResultState()
    data class Success(val summarySuccessContent: SummarySuccessContent) : SummaryResultState()
    data class Error(val errorMessage: String) : SummaryResultState()
}

data class SummarySuccessContent(
    val expensesList: List<SearchSingleResult>,
    val expensesGrouped: Map<String, List<SearchSingleResult>>,
    val summaryResultText: String,
)

@HiltViewModel
class SummaryScreenViewModel @Inject constructor(
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    private val searchServiceUseCase: SearchServiceUseCase,
    // TODO: Ensuring that all works, don't know if still needed
    private val appIsConfigured: AppIsConfigured,
) : ViewModel() {

    private val _searchForm = mutableStateOf(SummarySearchForm())
    val summarySearchForm: SummarySearchForm
        get() = _searchForm.value

    private var _summaryResultState: MutableState<SummaryResultState> = mutableStateOf(SummaryResultState.Loading)
    val summaryResultState: State<SummaryResultState>
        get() = _summaryResultState


    private var _categoriesList: MutableState<List<CategoryView>> = mutableStateOf(emptyList())
    val categoriesList: State<List<CategoryView>> =
        _categoriesList

    fun initScreen() {
        refreshResults()
    }

    fun updateSelectedCategory(newSelectedCategory: CategoryView) {
        _searchForm.value = _searchForm.value.copy(selectedCategory = newSelectedCategory)
        refreshResults()
    }

    fun updateQuickRangeData(newQuickRangeData: QuickRangeData) {
        _searchForm.value = _searchForm.value.copy(selectedQuickRangeData = newQuickRangeData)
        refreshResults()
    }

    fun updateSortElement(newSortElement: SortElement) {
        _searchForm.value = _searchForm.value.copy(selectedSortElement = newSortElement)
        refreshResults()
    }

    fun groupingCheckBoxChecked(newValue: Boolean) {
        _searchForm.value = _searchForm.value.copy(isGroupingEnabled = newValue)
        refreshResults()
    }

    fun updateGroupElement(groupElement: GroupElement) {
        _searchForm.value = _searchForm.value.copy(selectedGroupElement = groupElement)
        refreshResults()
    }

    fun updateAmountRangeStart(newAmountRangeStart: String) {
        _searchForm.value = _searchForm.value.copy(amountRangeStart = newAmountRangeStart)
        refreshResults()
    }

    fun updateAmountRangeEnd(newAmountRangeEnd: String) {
        _searchForm.value = _searchForm.value.copy(amountRangeEnd = newAmountRangeEnd)
        refreshResults()
    }

    fun refreshResults() {
        // TODO: czemu to się odpala 3x jak klikam dodajWydatek XDD
        viewModelScope.launch { // DONE
            try {
                _summaryResultState.value = SummaryResultState.Loading
                _summaryResultState.value = SummaryResultState.Success(prepareSummarySuccessContent())
            } catch (e: Exception) {
                Log.d("BK", "Exception: ${e.message}")
                _summaryResultState.value = SummaryResultState.Error(e.message
                    ?: "Unknown error sad times")
            }
        }
    }

    private suspend fun prepareSummarySuccessContent(): SummarySuccessContent {
        readCategoriesList()
        val summaryResult = searchServiceUseCase.invoke(summarySearchForm.toSearchCriteria())
        val expenses = summaryResult.expenses

        return SummarySuccessContent(
            expensesList = expenses,
            expensesGrouped = expenses.groupBy(summarySearchForm.selectedGroupElement.groupFunction),
            summaryResultText = summaryResult.averageExpenseResult.asTextSummary(),
        )
    }

    private suspend fun readCategoriesList() {
        _categoriesList.value = listOf(initialCategory()) + getCategoriesQuickSummaryUseCase.invoke().quickSummaries.map { it.toCategoryView() }
    }

}


fun SearchSingleResult.descriptionOrDefault(defaultDescription: String): String =
    if (description == EMPTY_STRING) defaultDescription else description


fun SearchAverageExpenseResult.asTextSummary(): String =
    "${wholeAmount.asPrintableAmount()} / $days d = ${averageAmount.asPrintableAmount()}/d"

fun CategoryQuickSummary.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = categoryName,
    )
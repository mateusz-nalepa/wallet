package com.mateuszcholyn.wallet.frontend.view.screen.summary

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchAverageExpenseResult
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.domain.appstate.AppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.groupingElements
import com.mateuszcholyn.wallet.frontend.view.dropdown.quickDateRanges
import com.mateuszcholyn.wallet.frontend.view.dropdown.sortingElements
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.CategoryView
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmount
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface WholeSummaryScreenState {
    data class Error(val message: String) : WholeSummaryScreenState
    data object Loading : WholeSummaryScreenState
    data object Visible : WholeSummaryScreenState
}

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


    val exposedWholeSummaryScreenState: MutableState<WholeSummaryScreenState> = mutableStateOf(WholeSummaryScreenState.Loading)
    private var wholeSummaryScreenState by MutableStateDelegate(exposedWholeSummaryScreenState)


    val exposedSummarySearchForm: MutableState<SummarySearchForm> = mutableStateOf(SummarySearchForm())
    private var summarySearchForm by MutableStateDelegate(exposedSummarySearchForm)
//

    val exposedSummaryResultState: MutableState<SummaryResultState> = mutableStateOf(SummaryResultState.Loading)
    private var summaryResultState by MutableStateDelegate(exposedSummaryResultState)

    fun updateSelectedCategory(newSelectedCategory: CategoryView) {
        summarySearchForm = summarySearchForm.copy(selectedCategory = newSelectedCategory)
        loadResultsFromDb()
    }

    fun updateQuickRangeData(newQuickRangeData: QuickRangeData) {
        summarySearchForm = summarySearchForm.copy(selectedQuickRangeData = newQuickRangeData)
        loadResultsFromDb()
    }

    fun updateSortElement(newSortElement: SortElement) {
        summarySearchForm = summarySearchForm.copy(selectedSortElement = newSortElement)
        loadResultsFromDb()
    }

    fun groupingCheckBoxChecked(newValue: Boolean) {
        summarySearchForm = summarySearchForm.copy(isGroupingEnabled = newValue)
        loadResultsFromDb()
    }

    fun updateGroupElement(groupElement: GroupElement) {
        summarySearchForm = summarySearchForm.copy(selectedGroupElement = groupElement)
        loadResultsFromDb()
    }

    fun updateAmountRangeStart(newAmountRangeStart: String) {
        summarySearchForm = summarySearchForm.copy(amountRangeStart = newAmountRangeStart)
        loadResultsFromDb()
    }

    fun updateAmountRangeEnd(newAmountRangeEnd: String) {
        summarySearchForm = summarySearchForm.copy(amountRangeEnd = newAmountRangeEnd)
        loadResultsFromDb()
    }

    fun initScreen() {
        viewModelScope.launch { // DONE UI State
            try {
                summarySearchForm =
                    summarySearchForm.copy(
                        categoriesList = readCategoriesList(),
                        quickDataRanges = quickDateRanges(),
                        sortElements = sortingElements(),
                        groupingElements = groupingElements(),
                    )
                wholeSummaryScreenState = WholeSummaryScreenState.Visible
                loadResultsFromDb()
            } catch (t: Throwable) {
                wholeSummaryScreenState = WholeSummaryScreenState.Error("Error podczas ładowania ekranu")

            }
        }
    }

    private suspend fun readCategoriesList(): List<CategoryView> {
        return listOf(CategoryView.default) + getCategoriesQuickSummaryUseCase.invoke().quickSummaries.map { it.toCategoryView() }
    }

    fun loadResultsFromDb() {
        viewModelScope.launch { // DONE UI State
            try {
                summaryResultState = SummaryResultState.Loading
                summaryResultState = SummaryResultState.Success(prepareSummarySuccessContent())
            } catch (e: Exception) {
                summaryResultState = SummaryResultState.Error("Nie udało się wczytać wyników")
            }
        }
    }

    private suspend fun prepareSummarySuccessContent(): SummarySuccessContent {
        val summaryResult = searchServiceUseCase.invoke(summarySearchForm.toSearchCriteria())
        val expenses = summaryResult.expenses

        return SummarySuccessContent(
            expensesList = expenses,
            expensesGrouped = expenses.groupBy(summarySearchForm.selectedGroupElement.groupFunction),
            summaryResultText = summaryResult.averageExpenseResult.asTextSummary(),
        )
    }

}


fun String.orDefaultDescription(defaultDescription: String): String =
    if (this == EMPTY_STRING) defaultDescription else this


fun SearchAverageExpenseResult.asTextSummary(): String =
    "${wholeAmount.asPrintableAmount()} / $days d = ${averageAmount.asPrintableAmount()}/d"

fun CategoryQuickSummary.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = categoryName,
    )

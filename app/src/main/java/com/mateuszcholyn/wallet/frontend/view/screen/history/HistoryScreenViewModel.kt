package com.mateuszcholyn.wallet.frontend.view.screen.history

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.MainCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchAverageExpenseResult
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.frontend.di.usecases.LocalDateTimeProvider
import com.mateuszcholyn.wallet.frontend.domain.appstate.DemoModeAppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.QuickRangeData
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.groupingElements
import com.mateuszcholyn.wallet.frontend.view.dropdown.quickDateRanges
import com.mateuszcholyn.wallet.frontend.view.dropdown.sortingElements
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.CsvGeneratorParameters
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.HistoryToCsvGenerator
import com.mateuszcholyn.wallet.frontend.view.screen.history.showSingleExpense.remove.RemoveSingleExpenseUiState
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.WalletMediaType
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.FileExportParameters
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.util.localDateTimeUtils.toHumanDateTimeText
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject


sealed interface HistoryScreenState {
    data class Error(val messageKey: Int) : HistoryScreenState
    data object Loading : HistoryScreenState
    data object Visible : HistoryScreenState
}

sealed class HistoryResultState {
    data object Loading : HistoryResultState()
    data class Success(val historySuccessContent: HistorySuccessContent) : HistoryResultState()
    data class Error(val errorMessageKey: Int) : HistoryResultState()
}

data class HistorySuccessContent(
    val expensesList: List<SearchSingleResult>,
    val expensesGrouped: Map<String, List<SearchSingleResult>>,
    val summaryResult: SearchAverageExpenseResult,
)

data class ExportToCsvUiState(
    val isLoading: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@HiltViewModel
class HistoryScreenViewModel @Inject constructor(
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    private val searchServiceUseCase: SearchServiceUseCase,
    private val removeExpenseUseCase: RemoveExpenseUseCase,
    private val demoModeAppIsConfigured: DemoModeAppIsConfigured,
    private val timeProvider: LocalDateTimeProvider,
    private val historyToCsvGenerator: HistoryToCsvGenerator,
) : ViewModel() { // done tests XD


    val exposedHistoryScreenState: MutableState<HistoryScreenState> =
        mutableStateOf(HistoryScreenState.Loading)
    private var historyScreenState by MutableStateDelegate(exposedHistoryScreenState)


    val exposedHistorySearchForm: MutableState<HistorySearchForm> =
        mutableStateOf(HistorySearchForm())
    private var historySearchForm by MutableStateDelegate(exposedHistorySearchForm)

    val exposedHistoryResultState: MutableState<HistoryResultState> =
        mutableStateOf(HistoryResultState.Loading)
    private var historyResultState by MutableStateDelegate(exposedHistoryResultState)

    var exportedExportUiState = mutableStateOf(ExportToCsvUiState())
        private set
    private var exportUiState by MutableStateDelegate(exportedExportUiState)

    fun updateSelectedCategory(newSelectedCategory: CategoryView) {
        historySearchForm = historySearchForm.copy(selectedCategory = newSelectedCategory)
        loadResultsFromDb()
    }

    fun updateQuickRangeData(newQuickRangeData: QuickRangeData) {
        val now = timeProvider.now()
        historySearchForm =
            if (newQuickRangeData.isCustomRangeData) {
                historySearchForm.copy(
                    selectedQuickRangeData = newQuickRangeData,
                    beginDate = now.minusDays(7),
                    endDate = now,
                    showCustomRangeDates = true,
                )
            } else {
                historySearchForm.copy(
                    selectedQuickRangeData = newQuickRangeData,
                    beginDate = newQuickRangeData.beginDate,
                    endDate = newQuickRangeData.endDate,
                    showCustomRangeDates = false,
                )
            }
        loadResultsFromDb()
    }

    fun updateBeginDate(newBeginDate: LocalDateTime) {
        historySearchForm = historySearchForm.copy(
            beginDate = newBeginDate,
        )
        loadResultsFromDb()
    }

    fun updateEndDate(newEndDate: LocalDateTime) {
        historySearchForm = historySearchForm.copy(
            endDate = newEndDate,
        )
        loadResultsFromDb()
    }

    fun updateSortElement(newSortElement: SortElement) {
        historySearchForm = historySearchForm.copy(selectedSortElement = newSortElement)
        loadResultsFromDb()
    }

    fun updateAdvancedFiltersVisible(newValue: Boolean) {
        historySearchForm = historySearchForm.copy(advancedOptionsVisible = newValue)
    }

    fun updateGroupingCheckBoxChecked(newValue: Boolean) {
        historySearchForm =
            if (newValue) {
                historySearchForm.copy(
                    isGroupingEnabled = newValue,
                    selectedSortElement = SortElement.default,
                )
            } else {
                historySearchForm.copy(
                    isGroupingEnabled = newValue,
                )
            }

        loadResultsFromDb()
    }

    fun updateGroupElement(groupElement: GroupElement) {
        historySearchForm = historySearchForm.copy(selectedGroupingElement = groupElement)
        loadResultsFromDb()
    }

    fun updateAmountRangeStart(newAmountRangeStart: String) {
        historySearchForm = historySearchForm.copy(amountRangeStart = newAmountRangeStart)
        loadResultsFromDb()
    }

    fun updateAmountRangeEnd(newAmountRangeEnd: String) {
        historySearchForm = historySearchForm.copy(amountRangeEnd = newAmountRangeEnd)
        loadResultsFromDb()
    }

    fun initScreen() {
        viewModelScope.launch { // DONE UI State
            try {
                historySearchForm =
                    historySearchForm.copy(
                        categoriesList = readCategoriesList(),
                        quickDataRanges = quickDateRanges(),
                        sortElements = sortingElements(),
                        groupingElements = groupingElements(),
                    )
                loadResultsFromDb()
                historyScreenState = HistoryScreenState.Visible
            } catch (t: Throwable) {
                println(t)
                historyScreenState =
                    HistoryScreenState.Error(R.string.error_unable_to_load_history_screen)

            }
        }
    }

    private suspend fun readCategoriesList(): List<CategoryView> =
        listOf(CategoryView.default) + getCategoriesQuickSummaryUseCase.invokeV2().quickSummaries.toCategoriesView()

    fun loadResultsFromDb() {
        viewModelScope.launch { // DONE UI State
            try {
                historyResultState = HistoryResultState.Loading
                historyResultState = HistoryResultState.Success(prepareSummarySuccessContent())
            } catch (e: Exception) {
                println(e)
                historyResultState =
                    HistoryResultState.Error(R.string.error_unable_to_load_expenses)
            }
        }
    }

    private suspend fun prepareSummarySuccessContent(): HistorySuccessContent {
        val summaryResult = searchServiceUseCase.invoke(historySearchForm.toSearchCriteria())
        val expenses = summaryResult.expenses

        return HistorySuccessContent(
            expensesList = expenses,
            expensesGrouped = expenses.groupBy(historySearchForm.selectedGroupingElement.groupFunction),
            summaryResult = summaryResult.averageExpenseResult,
        )
    }


    val exposedRemoveUiState: MutableState<RemoveSingleExpenseUiState> =
        mutableStateOf(RemoveSingleExpenseUiState())
    private var removeUiState by MutableStateDelegate(exposedRemoveUiState)

    fun closeRemoveModalDialog() {
        removeUiState = removeUiState.copy(isRemovalDialogVisible = false)
    }

    fun closeErrorModalDialog() {
        removeUiState = removeUiState.copy(errorModalState = ErrorModalState.NotVisible)
    }

    fun showRemoveConfirmationDialog() {
        removeUiState = removeUiState.copy(isRemovalDialogVisible = true)
    }

    fun removeExpenseById(
        expenseId: ExpenseId,
    ) {
        viewModelScope.launch { // DONE UI State - tak częściowo XD
            try {
                removeExpenseUseCase.invoke(expenseId)
                removeUiState = removeUiState.copy(isRemovalDialogVisible = false)
                loadResultsFromDb()
            } catch (t: Throwable) {
                removeUiState = removeUiState.copy(
                    isRemovalDialogVisible = false,
                    errorModalState = ErrorModalState.Visible(R.string.error_unable_to_remove_expense)
                )
            }
        }
    }

    fun closeExportErrorModalDialog() {
        exportUiState = exportUiState.copy(errorModalState = ErrorModalState.NotVisible)
    }

    fun exportToCsv(
        csvGeneratorParameters: CsvGeneratorParameters,
        onFileReadyAction: (FileExportParameters) -> Unit,
    ) {
        viewModelScope.launch { // DONE UI State
            try {
                exportUiState = exportUiState.copy(isLoading = true)
                unsafeExportHistoryToCsv(csvGeneratorParameters, onFileReadyAction)
                exportUiState = exportUiState.copy(isLoading = false)
            } catch (t: Throwable) {
                exportUiState = exportUiState.copy(
                    errorModalState = ErrorModalState.Visible(R.string.error_unable_to_export_data),
                    isLoading = false,
                )
            }
        }
    }

    private fun unsafeExportHistoryToCsv(
        csvGeneratorParameters: CsvGeneratorParameters,
        onFileReadyAction: (FileExportParameters) -> Unit,
    ) {
        val successResultState = historyResultState as HistoryResultState.Success

        val fileName = "${csvGeneratorParameters.fileNamePrefix}-${
            LocalDateTime.now().toHumanDateTimeText()
        }.csv"
        val fileContent =
            historyToCsvGenerator.generate(
                csvGeneratorParameters = csvGeneratorParameters,
                expensesList = successResultState.historySuccessContent.expensesList,
            )

        onFileReadyAction.invoke(
            FileExportParameters(
                fileName = fileName,
                fileContent = fileContent,
                title = csvGeneratorParameters.exportTitleLabel,
                mediaType = WalletMediaType.TEXT_CSV,
            )
        )
    }
}


fun String.orDefaultDescription(defaultDescription: String): String =
    if (this == EMPTY_STRING) defaultDescription else this


fun CategoryQuickSummary.toCategoryView(): CategoryView =
    CategoryView(
        categoryId = categoryId.id,
        name = this.categoryName,
    )


fun List<MainCategoryQuickSummary>.toCategoriesView(): List<CategoryView> =
    this.flatMap { mainCategory ->

        listOf(
            CategoryView(
                name = mainCategory.name,
                categoryId = mainCategory.id.id,
            )
        ) +
            mainCategory.subCategories.map { subCategory ->
                CategoryView(
                    name = mainCategory.name,
                    subName = subCategory.name,
                    categoryId = mainCategory.id.id,
                    subCategoryId = subCategory.id.id,
                )
            }
    }
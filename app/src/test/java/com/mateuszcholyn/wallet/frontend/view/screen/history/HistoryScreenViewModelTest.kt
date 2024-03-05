package com.mateuszcholyn.wallet.frontend.view.screen.history

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.frontend.domain.appstate.DemoModeAppIsConfigured
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.groupingElements
import com.mateuszcholyn.wallet.frontend.view.dropdown.quickDateRanges
import com.mateuszcholyn.wallet.frontend.view.dropdown.sortingElements
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.TestGetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.TestLocalDateTimeProvider
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.HistoryToCsvGenerator
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.testCsvGeneratorParameters
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.screen.util.fileUtils.export.FileExportParameters
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomExpenseId
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime
import java.util.Random

class HistoryScreenViewModelTest {

    private lateinit var viewModel: HistoryScreenViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testGetCategoriesQuickSummaryUseCase: TestGetCategoriesQuickSummaryUseCase = TestGetCategoriesQuickSummaryUseCase()
    private lateinit var removeExpenseUseCase: RemoveExpenseUseCase
    private lateinit var searchServiceUseCase: SearchServiceUseCase
    private lateinit var demoModeAppIsConfigured: DemoModeAppIsConfigured
    private val timeProvider: TestLocalDateTimeProvider = TestLocalDateTimeProvider()
    private lateinit var historyToCsvGenerator: HistoryToCsvGenerator

    @Before
    fun setUp() {
        removeExpenseUseCase = mockk<RemoveExpenseUseCase>(relaxed = true)
        searchServiceUseCase = mockk<SearchServiceUseCase>(relaxed = true)
        demoModeAppIsConfigured = mockk<DemoModeAppIsConfigured>(relaxed = true)
        historyToCsvGenerator = mockk<HistoryToCsvGenerator>(relaxed = true)

        viewModel =
            HistoryScreenViewModel(
                removeExpenseUseCase = removeExpenseUseCase,
                searchServiceUseCase = searchServiceUseCase,
                getCategoriesQuickSummaryUseCase = testGetCategoriesQuickSummaryUseCase,
                demoModeAppIsConfigured = demoModeAppIsConfigured,
                timeProvider = timeProvider,
                historyToCsvGenerator = historyToCsvGenerator,
            )
    }

    @Test
    fun defaultHistorySearchScreenShouldBeLoading() = runTest {
        // expect
        viewModel.exposedHistoryScreenState.value shouldBe HistoryScreenState.Loading
    }

    @Test
    fun stateShouldBeErrorWhenUnableToLoadCategoriesList() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willThrowException()

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedHistoryScreenState.value shouldBe
            HistoryScreenState.Error(R.string.error_unable_to_load_history_screen)
    }

    @Test
    fun stateShouldBeVisibleEvenIfUnableToLoadResultsFromDb() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())
        coEvery { searchServiceUseCase.invoke(any()) }.throws(RuntimeException())

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedHistoryScreenState.value shouldBe HistoryScreenState.Visible

        // and should show load results error
        viewModel.exposedHistoryResultState.value shouldBe HistoryResultState.Error(R.string.error_unable_to_load_expenses)
    }

    @Test
    fun thereAlwaysShouldBeOptionToShowResultsForAllCategories() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedHistorySearchForm.value.run {
            selectedCategory shouldBe CategoryView.default
            categoriesList shouldBe listOf(CategoryView.default)
        }
    }

    @Test
    fun shouldAlwaysCallSearchServiceOnInitScreen() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initScreen()

        // then
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateSelectedCategoryAndLoadResultsFromDb() = runTest {
        // given
        val givenNewCategory =
            CategoryView(
                name = "nazwa",
                categoryId = randomCategoryId().id,
            )

        // when
        viewModel.updateSelectedCategory(givenNewCategory)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            selectedCategory shouldBe givenNewCategory
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateSelectedQuickRangesAndLoadResultsFromDb() = runTest {
        // given
        val givenQuickRangeData = quickDateRanges().first()

        // when
        viewModel.updateQuickRangeData(givenQuickRangeData)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            selectedQuickRangeData shouldBe givenQuickRangeData
            beginDate shouldBe givenQuickRangeData.beginDate
            endDate shouldBe givenQuickRangeData.endDate
            showCustomRangeDates shouldBe false
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldSetSelectedQuickRangesToCustomAndLoadResultsFromDb() = runTest {
        // given
        val actualTime = LocalDateTime.now()
        timeProvider.willReturnTime(actualTime)
        val givenQuickRangeData = quickDateRanges().last()

        // when
        viewModel.updateQuickRangeData(givenQuickRangeData)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            selectedQuickRangeData shouldBe givenQuickRangeData
            beginDate shouldBe actualTime.minusDays(7)
            endDate shouldBe actualTime
            showCustomRangeDates shouldBe true
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateBeginDate() = runTest {
        // given
        val givenBeginDate = LocalDateTime.now()

        // when
        viewModel.updateBeginDate(givenBeginDate)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            beginDate shouldBe givenBeginDate
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateEndDate() = runTest {
        // given
        val givenEndDate = LocalDateTime.now()

        // when
        viewModel.updateEndDate(givenEndDate)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            endDate shouldBe givenEndDate
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateSelectedSortElementAndLoadResultsFromDb() = runTest {
        // given
        val givenSortElement = sortingElements().shuffled().first()

        // when
        viewModel.updateSortElement(givenSortElement)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            selectedSortElement shouldBe givenSortElement
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateGroupingCheckboxToEnabledAndLoadResultsFromDb() = runTest {
        // given
        val givenGroupingCheckboxEnabled = true

        // when
        viewModel.updateGroupingCheckBoxChecked(givenGroupingCheckboxEnabled)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            isGroupingEnabled shouldBe givenGroupingCheckboxEnabled
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
        // and selected sort is default
        viewModel.exposedHistorySearchForm.value.run {
            selectedSortElement shouldBe SortElement.default
        }
    }

    @Test
    fun shouldUpdateGroupingCheckboxToDisabledAndLoadResultsFromDb() = runTest {
        // given
        val givenGroupingCheckboxEnabled = false
        val existingSortElement: SortElement = sortingElements().last()

        // when
        viewModel.updateSortElement(existingSortElement)
        viewModel.updateGroupingCheckBoxChecked(givenGroupingCheckboxEnabled)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            isGroupingEnabled shouldBe givenGroupingCheckboxEnabled
        }
        // 2, cause first invoke is when updating sort element
        coVerify(exactly = 2) { searchServiceUseCase.invoke(any()) }
        // and selected sort is not changed
        viewModel.exposedHistorySearchForm.value.run {
            selectedSortElement shouldBe existingSortElement
        }
    }

    @Test
    fun shouldUpdateAdvancedFiltersVisibleAndNotLoadResultsFromDb() = runTest {
        // given
        val givenAdvancedFiltersVisible = Random().nextBoolean()

        // when
        viewModel.updateAdvancedFiltersVisible(givenAdvancedFiltersVisible)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            advancedOptionsVisible shouldBe givenAdvancedFiltersVisible
        }
        coVerify(exactly = 0) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateGroupElementAndLoadResultsFromDb() = runTest {
        // given
        val givenGroupElement = groupingElements().shuffled().first()

        // when
        viewModel.updateGroupElement(givenGroupElement)

        // then
        viewModel.exposedHistorySearchForm.value.run {
            selectedGroupingElement shouldBe givenGroupElement
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateAmountStartAndLoadResultsFromDb() = runTest {
        // given

        // when
        viewModel.updateAmountRangeStart("5")

        // then
        viewModel.exposedHistorySearchForm.value.run {
            amountRangeStart shouldBe "5"
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldUpdateAmountEndAndLoadResultsFromDb() = runTest {
        // given
        // when
        viewModel.updateAmountRangeEnd("5")

        // then
        viewModel.exposedHistorySearchForm.value.run {
            amountRangeEnd shouldBe "5"
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldShowDefaultSearchForm() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedHistorySearchForm.value.run {
            advancedOptionsVisible shouldBe false

            selectedCategory shouldBe CategoryView.default
            categoriesList shouldBe listOf(CategoryView.default)

            quickDataRanges.map { it.name } shouldBe quickDateRanges().map { it.name }
            showCustomRangeDates shouldBe false

            sortElements shouldBe sortingElements()
            selectedSortElement shouldBe SortElement.default

            amountRangeStart shouldBe 0.toString()
            amountRangeEnd shouldBe Int.MAX_VALUE.toString()

            groupingElements shouldBe groupingElements()
            selectedGroupingElement shouldBe GroupElement.default
            isGroupingEnabled shouldBe false
        }
    }

    @Test
    fun shouldOpenAndCloseRemoveConfirmationDialog() = runTest {
        // given
        viewModel.initScreen()

        // when
        viewModel.showRemoveConfirmationDialog()

        // then
        viewModel.exposedRemoveUiState.value.run {
            isRemovalDialogVisible shouldBe true
        }

        // and when
        viewModel.closeRemoveModalDialog()

        // and then
        viewModel.exposedRemoveUiState.value.run {
            isRemovalDialogVisible shouldBe false
        }
        coVerify(exactly = 0) { removeExpenseUseCase.invoke(any()) }
    }

    @Test
    fun shouldRemoveExpenseAndRefreshScreen() = runTest {
        // given
        viewModel.initScreen()

        // when
        viewModel.showRemoveConfirmationDialog()

        // and confirm removal which also refresh screen
        viewModel.removeExpenseById(randomExpenseId())

        // then
        viewModel.exposedRemoveUiState.value.run {
            isRemovalDialogVisible shouldBe false
        }
        // first in init, second when removing expense
        coVerify(exactly = 2) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun shouldShowErrorModalDialogWhenRemovingExpenseCausesAndError() = runTest {
        // given
        viewModel.initScreen()
        coEvery { removeExpenseUseCase.invoke(any()) }.throws(RuntimeException())

        // when
        viewModel.showRemoveConfirmationDialog()
        viewModel.removeExpenseById(randomExpenseId())

        // then
        viewModel.exposedRemoveUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_remove_expense)
        }
        coVerify(exactly = 1) { removeExpenseUseCase.invoke(any()) }

        // and when close error modal
        viewModel.closeErrorModalDialog()

        // and then
        viewModel.exposedRemoveUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun shouldShowErrorModalWhenUnableToGenerateBackupFile() = runTest {
        // given
        coEvery { historyToCsvGenerator.generate(any(), any()) }.throws(RuntimeException())
        val onFileReadyActionMock = mockk<(FileExportParameters) -> Unit>()

        // when
        viewModel.exportToCsv(
            csvGeneratorParameters = testCsvGeneratorParameters,
            onFileReadyAction = onFileReadyActionMock,
        )

        // then
        viewModel.exportedExportUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_export_data)
        }
        // and
        verify(exactly = 0) { onFileReadyActionMock.invoke(any()) }

        // and should close error modal
        viewModel.closeExportErrorModalDialog()

        // and then
        viewModel.exportedExportUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

}
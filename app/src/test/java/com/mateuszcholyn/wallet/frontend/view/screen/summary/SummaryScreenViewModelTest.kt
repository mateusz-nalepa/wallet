package com.mateuszcholyn.wallet.frontend.view.screen.summary

import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.dropdown.GroupElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.SortElement
import com.mateuszcholyn.wallet.frontend.view.dropdown.groupingElements
import com.mateuszcholyn.wallet.frontend.view.dropdown.quickDateRanges
import com.mateuszcholyn.wallet.frontend.view.dropdown.sortingElements
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.TestGetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.summary.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.manager.randomCategoryId
import com.mateuszcholyn.wallet.manager.randomExpenseId
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.Random

class SummaryScreenViewModelTest {

    private lateinit var viewModel: SummaryScreenViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testGetCategoriesQuickSummaryUseCase: TestGetCategoriesQuickSummaryUseCase = TestGetCategoriesQuickSummaryUseCase()
    private lateinit var removeExpenseUseCase: RemoveExpenseUseCase
    private lateinit var searchServiceUseCase: SearchServiceUseCase

    @Before
    fun setUp() {
        removeExpenseUseCase = mockk<RemoveExpenseUseCase>(relaxed = true)
        searchServiceUseCase = mockk<SearchServiceUseCase>(relaxed = true)

        viewModel =
            SummaryScreenViewModel(
                removeExpenseUseCase = removeExpenseUseCase,
                searchServiceUseCase = searchServiceUseCase,
                getCategoriesQuickSummaryUseCase = testGetCategoriesQuickSummaryUseCase,
            )
    }

    @Test
    fun `default history search screen should be loading`() = runTest {
        // expect
        viewModel.exposedWholeSummaryScreenState.value shouldBe WholeSummaryScreenState.Loading
    }

    @Test
    fun `state should be error when unable to load categories list`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willThrowException()

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedWholeSummaryScreenState.value shouldBe
            WholeSummaryScreenState.Error("Error podczas ładowania ekranu")
    }

    @Test
    fun `state should be visible even if unable to load results from db`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())
        coEvery { searchServiceUseCase.invoke(any()) }.throws(RuntimeException())

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedWholeSummaryScreenState.value shouldBe WholeSummaryScreenState.Visible

        // and should show load results error
        viewModel.exposedSummaryResultState.value shouldBe SummaryResultState.Error("Nie udało się wczytać wyników")
    }

    @Test
    fun `there always should be option to show results for all categories`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedSummarySearchForm.value.run {
            selectedCategory shouldBe CategoryView.default
            categoriesList shouldBe listOf(CategoryView.default)
        }
    }

    @Test
    fun `should always call searchService on initScreen`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initScreen()

        // then
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update selected category and load results from db`() = runTest {
        // given
        val givenNewCategory =
            CategoryView(
                name = "nazwa",
                categoryId = randomCategoryId().id,
            )

        // when
        viewModel.updateSelectedCategory(givenNewCategory)

        // then
        viewModel.exposedSummarySearchForm.value.run {
            selectedCategory shouldBe givenNewCategory
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update selected quick ranges and load results from db`() = runTest {
        // given
        val givenQuickRangeData = quickDateRanges().shuffled().first()

        // when
        viewModel.updateQuickRangeData(givenQuickRangeData)

        // then
        viewModel.exposedSummarySearchForm.value.run {
            selectedQuickRangeData shouldBe givenQuickRangeData
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update selected sort element and load results from db`() = runTest {
        // given
        val givenSortElement = sortingElements().shuffled().first()

        // when
        viewModel.updateSortElement(givenSortElement)

        // then
        viewModel.exposedSummarySearchForm.value.run {
            selectedSortElement shouldBe givenSortElement
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update grouping checkbox enabled and load results from db`() = runTest {
        // given
        val givenGroupingCheckboxEnabled = Random().nextBoolean()

        // when
        viewModel.updateGroupingCheckBoxChecked(givenGroupingCheckboxEnabled)

        // then
        viewModel.exposedSummarySearchForm.value.run {
            isGroupingEnabled shouldBe givenGroupingCheckboxEnabled
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update advanced filters visible and not load results from db`() = runTest {
        // given
        val givenAdvancedFiltersVisible = Random().nextBoolean()

        // when
        viewModel.updateAdvancedFiltersVisible(givenAdvancedFiltersVisible)

        // then
        viewModel.exposedSummarySearchForm.value.run {
            advancedFiltersVisible shouldBe givenAdvancedFiltersVisible
        }
        coVerify(exactly = 0) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update group element and load results from db`() = runTest {
        // given
        val givenGroupElement = groupingElements().shuffled().first()

        // when
        viewModel.updateGroupElement(givenGroupElement)

        // then
        viewModel.exposedSummarySearchForm.value.run {
            selectedGroupingElement shouldBe givenGroupElement
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update amount start and load results from db`() = runTest {
        // given

        // when
        viewModel.updateAmountRangeStart("5")

        // then
        viewModel.exposedSummarySearchForm.value.run {
            amountRangeStart shouldBe "5"
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should update amount end and load results from db`() = runTest {
        // given
        // when
        viewModel.updateAmountRangeEnd("5")

        // then
        viewModel.exposedSummarySearchForm.value.run {
            amountRangeEnd shouldBe "5"
        }
        coVerify(exactly = 1) { searchServiceUseCase.invoke(any()) }
    }

    @Test
    fun `should show default search form`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initScreen()

        // then
        viewModel.exposedSummarySearchForm.value.run {
            advancedFiltersVisible shouldBe false

            selectedCategory shouldBe CategoryView.default
            categoriesList shouldBe listOf(CategoryView.default)

            quickDataRanges.map { it.name } shouldBe quickDateRanges().map { it.name }

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
    fun `should open and close remove confirmation dialog`() = runTest {
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
    fun `should remove expense and refresh screen`() = runTest {
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
    fun `should show error modal dialog when removing expense causes and error`() = runTest {
        // given
        viewModel.initScreen()
        coEvery { removeExpenseUseCase.invoke(any()) }.throws(RuntimeException())

        // when
        viewModel.showRemoveConfirmationDialog()
        viewModel.removeExpenseById(randomExpenseId())

        // then
        viewModel.exposedRemoveUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible("usuwanie się nie udało")
        }
        coVerify(exactly = 1) { removeExpenseUseCase.invoke(any()) }

        // and when close error modal
        viewModel.closeErrorModalDialog()

        // and then
        viewModel.exposedRemoveUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

}
package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.randomCategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.view.screen.summary.toCategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import io.kotest.matchers.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.time.LocalDateTime


class ExpenseFormViewModelTest {

    private lateinit var viewModel: ExpenseFormViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testGetCategoriesQuickSummaryUseCase: TestGetCategoriesQuickSummaryUseCase = TestGetCategoriesQuickSummaryUseCase()
    private val testLocalDateTimeProvider: TestLocalDateTimeProvider = TestLocalDateTimeProvider()

    @Before
    fun setUp() {
        viewModel =
            ExpenseFormViewModel(
                localDateTimeProvider = testLocalDateTimeProvider,
                addExpenseUseCase = TestAddExpenseUseCase(),
                updateExpenseUseCase = TestUpdateExpenseUseCase(),
                getExpenseUseCase = TestGetExpenseUseCase(),
                getCategoriesQuickSummaryUseCase = testGetCategoriesQuickSummaryUseCase,
            )
    }

    @Test
    fun `default ExpenseFormScreenState should be loading`() = runTest {
        // expect
        assert(viewModel.exportedExpenseFormScreenState.value is ExpenseFormScreenState.Loading)
    }

    @Test
    fun `expense form screen should be ExpenseFormScreenState - NoCategories when there is no categories`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initExpenseFormScreenExt()

        // then
        assert(viewModel.exportedExpenseFormScreenState.value is ExpenseFormScreenState.NoCategories)
    }

    @Test
    fun `should show expense form where there are categories present`() = runTest {
        // given
        val actualTime = LocalDateTime.now()
        testLocalDateTimeProvider.willReturnTime(actualTime)
        val givenCategories =
            listOf(
                randomCategoryQuickSummary(),
                randomCategoryQuickSummary(),
            )
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(givenCategories)

        // when
        viewModel.initExpenseFormScreenExt()

        // then

        viewModel.exportedExpenseFormDetailsUiState.value.run {
            assert(submitButtonLabel == "Dodaj wydatek")
            assert(categories == givenCategories.map { it.toCategoryView() })
            assert(selectedCategory == givenCategories.first().toCategoryView())
            assert(paidAt == actualTime)
        }
        assert(viewModel.exportedExpenseFormScreenState.value is ExpenseFormScreenState.Show)
    }

    @Test
    fun `should throw exception when trying to save expense when amount is empty`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(randomCategoryQuickSummary()))
        viewModel.initExpenseFormScreenExt()
        viewModel.updateAmount(EMPTY_STRING)

        // when
        viewModel.saveExpense()

        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible("wywaliło się podczas dodawania")
            expenseSubmitButtonState shouldBe ExpenseSubmitButtonState.ENABLED
        }
    }


}

private fun ExpenseFormViewModel.initExpenseFormScreenExt(
    actualExpenseId: String? = null,
    screenMode: String? = null,
    onButtonSubmittedAction: () -> Unit = {},
) {
    this.initExpenseFormScreen(
        actualExpenseId = actualExpenseId,
        screenMode = screenMode,
        onButtonSubmittedAction = onButtonSubmittedAction,
    )
}
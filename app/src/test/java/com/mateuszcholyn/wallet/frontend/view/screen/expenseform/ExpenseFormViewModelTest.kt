package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.randomCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.CategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.history.toCategoryView
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import com.mateuszcholyn.wallet.frontend.view.util.asPrintableAmountWithoutCurrencySymbol
import com.mateuszcholyn.wallet.frontend.view.util.withTwoSignsPrecision
import com.mateuszcholyn.wallet.manager.randomExpenseWithCategory
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUTCInstantToUserLocalTimeZone
import com.mateuszcholyn.wallet.util.localDateTimeUtils.fromUserLocalTimeZoneToUTCInstant
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.math.BigDecimal
import java.time.LocalDateTime


class ExpenseFormViewModelTest {

    private lateinit var viewModel: ExpenseFormViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var addExpenseUseCase: AddExpenseUseCase
    private lateinit var updateExpenseUseCase: UpdateExpenseUseCase
    private lateinit var getExpenseUseCase: GetExpenseUseCase

    private val testGetCategoriesQuickSummaryUseCase: TestGetCategoriesQuickSummaryUseCase =
        TestGetCategoriesQuickSummaryUseCase()
    private val timeProvider: TestLocalDateTimeProvider = TestLocalDateTimeProvider()

    @Before
    fun setUp() {
        addExpenseUseCase = mockk<AddExpenseUseCase>(relaxed = true)
        updateExpenseUseCase = mockk<UpdateExpenseUseCase>(relaxed = true)
        getExpenseUseCase = mockk<GetExpenseUseCase>(relaxed = true)

        viewModel =
            ExpenseFormViewModel(
                localDateTimeProvider = timeProvider,
                addExpenseUseCase = addExpenseUseCase,
                updateExpenseUseCase = updateExpenseUseCase,
                getExpenseUseCase = getExpenseUseCase,
                getCategoriesQuickSummaryUseCase = testGetCategoriesQuickSummaryUseCase,
            )
    }

    @Test
    fun defaultExpenseFormScreenStateShouldBeLoading() = runTest {
        // expect
        assert(viewModel.exportedExpenseFormScreenState.value is ExpenseFormScreenState.Loading)
    }

    @Test
    fun expenseFormScreenShouldBeExpenseFormScreenStateNoCategoriesWhenThereIsNoCategories() =
        runTest {
            // given
            testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

            // when
            viewModel.initExpenseFormScreenExt()

            // then
            assert(viewModel.exportedExpenseFormScreenState.value is ExpenseFormScreenState.NoCategories)
        }

    @Test
    fun expenseFormScreenShouldBeExpenseFormScreenStateErrorWhenGetCategoriesThrowsError() =
        runTest {
            // given
            testGetCategoriesQuickSummaryUseCase.willThrowException()

            // when
            viewModel.initExpenseFormScreenExt()

            // then
            viewModel.exportedExpenseFormScreenState.value shouldBe ExpenseFormScreenState.Error("error on init XD")
        }


    @Test
    fun shouldShowExpenseFormWhereThereAreCategoriesPresent() = runTest {
        // given
        val actualTime = LocalDateTime.now()
        timeProvider.willReturnTime(actualTime)
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
            assert(submitButtonLabelKey == R.string.button_addExpense)
            assert(categories == givenCategories.map { it.toCategoryView() })
            assert(selectedCategory == givenCategories.first().toCategoryView())
            assert(paidAt == actualTime)
        }
        assert(viewModel.exportedExpenseFormScreenState.value is ExpenseFormScreenState.Show)
    }

    @Test
    fun shouldThrowExceptionWhenTryingToSaveExpenseWhenAmountIsEmpty() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(randomCategoryQuickSummary()))
        viewModel.initExpenseFormScreenExt()
        viewModel.updateAmount(EMPTY_STRING)

        // when
        viewModel.saveExpense()

        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_addExpense)
            expenseSubmitButtonState shouldBe ExpenseSubmitButtonState.ENABLED
        }

        // and when close error modal
        viewModel.closeErrorModal()

        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun shouldUpdateCategory() = runTest {
        // given
        val categories =
            listOf(
                randomCategoryQuickSummary(),
                randomCategoryQuickSummary(),
            )

        testGetCategoriesQuickSummaryUseCase.willReturnCategories(categories)
        viewModel.initExpenseFormScreenExt()

        // when
        viewModel.updateCategory(categories.last().toCategoryView())

        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            selectedCategory shouldBe categories.last().toCategoryView()
        }
    }

    @Test
    fun shouldAddExpense() = runTest {
        // given
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        val givenCategory = randomCategoryQuickSummary()

        val time = LocalDateTime.now()
        timeProvider.willReturnTime(time)
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        viewModel.initExpenseFormScreenExt(
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )

        // when
        viewModel.updateAmount("15")
        viewModel.updateDescription("description")
        viewModel.saveExpense()

        // then
        verify(exactly = 1) { onButtonSubmittedActionMock.invoke() }
        coVerify(exactly = 1) {
            addExpenseUseCase.invoke(
                AddExpenseParameters(
                    expenseId = null,
                    amount = BigDecimal.valueOf(15),
                    description = "description",
                    paidAt = time.fromUserLocalTimeZoneToUTCInstant(),
                    categoryId = givenCategory.categoryId,
                )
            )
        }
    }

    @Test
    fun shouldShowInfoAboutInvalidAmountAndButtonShouldBeDisabled() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(randomCategoryQuickSummary()))
        viewModel.initExpenseFormScreenExt()

        // when
        viewModel.updateAmount("some invalid amount value")

        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            isAmountInvalid shouldBe true
            expenseSubmitButtonState shouldBe ExpenseSubmitButtonState.DISABLED
        }
    }

    @Test
    fun shouldShowErrorStateModalWhenAddingExpenseThrowsError() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary()
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        viewModel.initExpenseFormScreenExt()
        coEvery { addExpenseUseCase.invoke(any()) }.throws(RuntimeException("XD"))

        // when
        viewModel.fillFormWithDummyData()
        viewModel.saveExpense()

        // then
        coVerify(exactly = 1) {
            addExpenseUseCase.invoke(any())
        }
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_addExpense)
            expenseSubmitButtonState shouldBe ExpenseSubmitButtonState.ENABLED
        }
    }

    @Test
    fun shouldOpenScreenInUpdateExpenseMode() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary()
        val expenseToBeUpdated =
            randomExpenseWithCategory(
                categoryQuickSummary = givenCategory,
            )
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        coEvery { getExpenseUseCase.invoke(expenseToBeUpdated.expenseId) }.returns(
            expenseToBeUpdated
        )

        // when
        viewModel.initExpenseFormScreenExt(
            actualExpenseId = expenseToBeUpdated.expenseId.id,
        )
        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            actualExpenseId shouldBe expenseToBeUpdated.expenseId.id
            amount shouldBe expenseToBeUpdated.amount.asPrintableAmountWithoutCurrencySymbol()
            isAmountInvalid shouldBe false
            description shouldBe expenseToBeUpdated.description
            selectedCategory shouldBe CategoryView(
                categoryId = expenseToBeUpdated.categoryId.id,
                name = expenseToBeUpdated.categoryName,
            )
            paidAt shouldBe expenseToBeUpdated.paidAt.fromUTCInstantToUserLocalTimeZone()

            submitButtonLabelKey shouldBe R.string.button_editExpense
            expenseSubmitButtonState shouldBe ExpenseSubmitButtonState.ENABLED
        }
    }

    @Test
    fun shouldUpdateExpense() = runTest {
        // given
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        val givenCategory = randomCategoryQuickSummary()
        val expenseToBeUpdated =
            randomExpenseWithCategory(
                categoryQuickSummary = givenCategory,
            )
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        coEvery { getExpenseUseCase.invoke(expenseToBeUpdated.expenseId) }.returns(
            expenseToBeUpdated
        )

        viewModel.initExpenseFormScreenExt(
            actualExpenseId = expenseToBeUpdated.expenseId.id,
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )

        // when
        viewModel.saveExpense()

        // then
        coVerify(exactly = 1) {
            updateExpenseUseCase.invoke(
                Expense(
                    expenseId = expenseToBeUpdated.expenseId,
                    amount = expenseToBeUpdated.amount.withTwoSignsPrecision(),
                    description = expenseToBeUpdated.description,
                    paidAt = expenseToBeUpdated.paidAt,
                    categoryId = givenCategory.categoryId,
                )
            )
        }
        verify(exactly = 1) { onButtonSubmittedActionMock.invoke() }
    }

    @Test
    fun shouldOpenScreenInCopyExpenseMode() = runTest {
        // given
        val time = LocalDateTime.now()
        timeProvider.willReturnTime(time)
        val givenCategory = randomCategoryQuickSummary()
        val expenseToBeUpdated =
            randomExpenseWithCategory(
                categoryQuickSummary = givenCategory,
            )
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        coEvery { getExpenseUseCase.invoke(expenseToBeUpdated.expenseId) }.returns(
            expenseToBeUpdated
        )

        // when
        viewModel.initExpenseFormScreenExt(
            actualExpenseId = expenseToBeUpdated.expenseId.id,
            screenMode = "copy",
        )
        // then
        viewModel.exportedExpenseFormDetailsUiState.value.run {
            actualExpenseId shouldBe expenseToBeUpdated.expenseId.id
            amount shouldBe expenseToBeUpdated.amount.asPrintableAmountWithoutCurrencySymbol()
            isAmountInvalid shouldBe false
            description shouldBe expenseToBeUpdated.description
            selectedCategory shouldBe CategoryView(
                categoryId = expenseToBeUpdated.categoryId.id,
                name = expenseToBeUpdated.categoryName,
            )
            paidAt shouldBe time

            submitButtonLabelKey shouldBe R.string.button_copyExpense
            expenseSubmitButtonState shouldBe ExpenseSubmitButtonState.ENABLED
        }
    }

    @Test
    fun shouldAddANewExpenseWhenCopyingAnExpense() = runTest {
        // given
        val time = LocalDateTime.now()
        timeProvider.willReturnTime(time)
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        val givenCategory = randomCategoryQuickSummary()
        val expenseToBeUpdated =
            randomExpenseWithCategory(
                categoryQuickSummary = givenCategory,
            )
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        coEvery { getExpenseUseCase.invoke(expenseToBeUpdated.expenseId) }.returns(
            expenseToBeUpdated
        )

        viewModel.initExpenseFormScreenExt(
            actualExpenseId = expenseToBeUpdated.expenseId.id,
            screenMode = "copy",
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )

        // when
        viewModel.saveExpense()

        // then
        coVerify(exactly = 1) {
            addExpenseUseCase.invoke(
                AddExpenseParameters(
                    amount = expenseToBeUpdated.amount.withTwoSignsPrecision(),
                    description = expenseToBeUpdated.description,
                    paidAt = time.fromUserLocalTimeZoneToUTCInstant(),
                    categoryId = givenCategory.categoryId,
                )
            )
        }
        verify(exactly = 1) { onButtonSubmittedActionMock.invoke() }
    }

    // test na to, że form invalid jak kwota invalid


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

private fun ExpenseFormViewModel.fillFormWithDummyData(
) {
    this.updateAmount("15")
    this.updateDescription("description")
}
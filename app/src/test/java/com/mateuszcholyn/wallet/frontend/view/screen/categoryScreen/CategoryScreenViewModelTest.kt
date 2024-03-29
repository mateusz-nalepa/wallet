package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.randomCategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.TestGetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoryScreenViewModelTest {

    private lateinit var viewModel: CategoryScreenViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var removeCategoryUseCase: RemoveCategoryUseCase
    private val testGetCategoriesQuickSummaryUseCase: TestGetCategoriesQuickSummaryUseCase = TestGetCategoriesQuickSummaryUseCase()

    @Before
    fun setUp() {
        removeCategoryUseCase = mockk<RemoveCategoryUseCase>(relaxed = true)

        viewModel =
            CategoryScreenViewModel(
                removeCategoryUseCase = removeCategoryUseCase,
                getCategoriesQuickSummaryUseCase = testGetCategoriesQuickSummaryUseCase,
            )
    }

    @Test
    fun defaultCategoryScreenStateShouldBeLoading() = runTest {
        // expect
        viewModel.exportedCategoryScreenState.value shouldBe CategoryScreenState.Loading
    }

    @Test
    fun stateShouldBeErrorWhenUnableToLoadCategoriesSummary() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willThrowException()

        // when
        viewModel.refreshScreen()

        // then
        viewModel.exportedCategoryScreenState.value shouldBe CategoryScreenState.Error(R.string.error_unable_to_load_categories)
    }

    @Test
    fun stateShouldBeSuccessWhenLoadedCategories() = runTest {
        // given
        val givenCategories =
            listOf(
                randomCategoryQuickSummary(),
                randomCategoryQuickSummary(),
            )
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(givenCategories)

        // when
        viewModel.refreshScreen()

        // then
        viewModel.exportedCategoryScreenState.value shouldBe CategoryScreenState.Success(
            CategorySuccessContent(categoriesList = givenCategories)
        )
    }

    @Test
    fun shouldOpenAndCloseRemoveConfirmationDialog() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary(numberOfExpenses = 0)
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        viewModel.refreshScreen()

        // when
        viewModel.onRemoveCategoryModalOpen()

        // then
        viewModel.exportedRemoveCategoryState.value.run {
            categoryRemoveConfirmationDialogIsVisible shouldBe true
        }

        // and when
        viewModel.onRemoveCategoryModalClose()

        // and then
        viewModel.exportedRemoveCategoryState.value.run {
            categoryRemoveConfirmationDialogIsVisible shouldBe false
        }
        coVerify(exactly = 0) { removeCategoryUseCase.invoke(any()) }
    }

    @Test
    fun shouldRemoveCategoryWhenNumberOfExpensesIs0() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary(numberOfExpenses = 0)
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        viewModel.refreshScreen()

        // when
        viewModel.onRemoveCategoryModalOpen()

        // then
        viewModel.exportedRemoveCategoryState.value.run {
            categoryRemoveConfirmationDialogIsVisible shouldBe true
        }

        // and confirm removal which also refresh screen
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())
        viewModel.removeCategory(givenCategory.categoryId)

        // then
        viewModel.exportedCategoryScreenState.value shouldBe CategoryScreenState.Success(
            CategorySuccessContent(categoriesList = emptyList())
        )
        coVerify(exactly = 1) { removeCategoryUseCase.invoke(givenCategory.categoryId) }
    }

    @Test
    fun shouldShowErrorModalDialogWhenCategoryHasAnyExpenses() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary(numberOfExpenses = 5)
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        viewModel.refreshScreen()

        // when
        viewModel.onRemoveCategoryModalOpen()

        // then
        viewModel.exportedRemoveCategoryState.value.run {
            categoryRemoveConfirmationDialogIsVisible shouldBe true
        }

        // and confirm removal which also refresh screen
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())
        viewModel.removeCategory(givenCategory.categoryId)

        // then
        viewModel.exportedRemoveCategoryState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_remove_category_where_are_expenses)
        }
        coVerify(exactly = 0) { removeCategoryUseCase.invoke(givenCategory.categoryId) }

        // and when close error modal
        viewModel.closeErrorModal()

        // and then
        viewModel.exportedRemoveCategoryState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun shouldShowErrorModalDialogWhenRemovingExpenseCausesAndError() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary(numberOfExpenses = 0)
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))
        viewModel.refreshScreen()
        coEvery { removeCategoryUseCase.invoke(any()) }.throws(RuntimeException())

        // when
        viewModel.onRemoveCategoryModalOpen()
        viewModel.removeCategory(givenCategory.categoryId)

        // then
        viewModel.exportedRemoveCategoryState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_remove_category)
        }
        coVerify(exactly = 1) { removeCategoryUseCase.invoke(givenCategory.categoryId) }

        // and when close error modal
        viewModel.closeErrorModal()

        // and then
        viewModel.exportedRemoveCategoryState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }



}
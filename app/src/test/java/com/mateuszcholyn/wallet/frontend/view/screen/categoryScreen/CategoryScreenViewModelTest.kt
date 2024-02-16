package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

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
    fun `default CategoryScreenState should be loading`() = runTest {
        // expect
        viewModel.exportedCategoryScreenState.value shouldBe CategoryScreenState.Loading
    }

    @Test
    fun `state should be error when unable to load categories summary`() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willThrowException()

        // when
        viewModel.refreshScreen()

        // then
        viewModel.exportedCategoryScreenState.value shouldBe CategoryScreenState.Error("Unknown error sad times")
    }

    @Test
    fun `state should be success when loaded categories`() = runTest {
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
    fun `should open and close remove confirmation dialog`() = runTest {
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
    fun `should remove category when number of expenses is 0`() = runTest {
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
    fun `should show error modal dialog when category has any expenses`() = runTest {
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
            errorModalState shouldBe ErrorModalState.Visible("Nie możesz usunąć kategorii gdzie są wydatki")
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
    fun `should show error modal dialog when removing expense causes and error`() = runTest {
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
            errorModalState shouldBe ErrorModalState.Visible("error podczas usuwania")
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
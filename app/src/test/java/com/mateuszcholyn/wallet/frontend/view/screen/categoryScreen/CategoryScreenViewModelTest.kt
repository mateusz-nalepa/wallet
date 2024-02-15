package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.TestGetCategoriesQuickSummaryUseCase
import io.kotest.matchers.shouldBe
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

}
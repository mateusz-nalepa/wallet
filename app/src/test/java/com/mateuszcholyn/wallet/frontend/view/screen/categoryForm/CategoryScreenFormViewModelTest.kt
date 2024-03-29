package com.mateuszcholyn.wallet.frontend.view.screen.categoryForm

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.randomCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategorySubmitButton
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.MainDispatcherRule
import com.mateuszcholyn.wallet.frontend.view.screen.expenseform.TestGetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import com.mateuszcholyn.wallet.frontend.view.util.EMPTY_STRING
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CategoryScreenFormViewModelTest {


    private lateinit var viewModel: CategoryScreenFormViewModel

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val testGetCategoriesQuickSummaryUseCase: TestGetCategoriesQuickSummaryUseCase = TestGetCategoriesQuickSummaryUseCase()
    private lateinit var createCategoryUseCase: CreateCategoryUseCase
    private lateinit var updateCategoryUseCase: UpdateCategoryUseCase

    @Before
    fun setUp() {
        createCategoryUseCase = mockk<CreateCategoryUseCase>(relaxed = true)
        updateCategoryUseCase = mockk<UpdateCategoryUseCase>(relaxed = true)

        viewModel =
            CategoryScreenFormViewModel(
                createCategoryUseCase = createCategoryUseCase,
                updateCategoryUseCase = updateCategoryUseCase,
                getCategoriesQuickSummaryUseCase = testGetCategoriesQuickSummaryUseCase,
            )
    }

    @Test
    fun defaultCategoryFromStateShouldBeLoading() = runTest {
        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Loading
    }

    @Test
    fun CategoryFromStateShouldBeErrorWhenUnableToReadCategoriesList() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willThrowException()

        // when
        viewModel.initCategoryFormScreenExt()

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Error
    }


    @Test
    fun shouldOpenScreenInAddMode() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        // when
        viewModel.initCategoryFormScreenExt(
            existingCategoryId = null,
        )

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        viewModel.exportedCategoryFormUiState.value.run {
            categoryName shouldBe EMPTY_STRING
            isFormInvalid shouldBe false
            submitButtonState shouldBe CategorySubmitButton.DISABLED
            showCategoryAlreadyExistsWarning shouldBe false
            errorModalState shouldBe ErrorModalState.NotVisible
            buttonLabelKey shouldBe R.string.button_addCategory
        }
    }

    @Test
    fun formShouldBeInInvalidStateWhenUpdatedCategoryNameIsEmpty() = runTest {
        // given
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        viewModel.initCategoryFormScreenExt()
        // when
        viewModel.updateCategoryFormName(EMPTY_STRING)


        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        viewModel.exportedCategoryFormUiState.value.run {
            categoryName shouldBe EMPTY_STRING
            isFormInvalid shouldBe true
            submitButtonState shouldBe CategorySubmitButton.DISABLED
        }

        // and
        viewModel.updateCategoryFormName("some category name")
        viewModel.exportedCategoryFormUiState.value.run {
            categoryName shouldBe "some category name"
            isFormInvalid shouldBe false
            submitButtonState shouldBe CategorySubmitButton.ENABLED
        }
    }

    @Test
    fun shouldShowErrorModalWhenUnableToAddCategory() = runTest {
        // given
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        coEvery { createCategoryUseCase.invoke(any()) }.throws(RuntimeException("XDDD"))
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        viewModel.initCategoryFormScreenExt(
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )
        viewModel.updateCategoryFormName("some category")
        // when
        viewModel.saveCategory()

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        viewModel.exportedCategoryFormUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_addCategory)
        }
        coVerify(exactly = 0) { onButtonSubmittedActionMock.invoke() }

        // and when
        viewModel.closeErrorModal()

        // then
        viewModel.exportedCategoryFormUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun shouldAddNewCategory() = runTest {
        val givenCategoryName = "some category"
        // given
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(emptyList())

        viewModel.initCategoryFormScreenExt(
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )
        viewModel.updateCategoryFormName(givenCategoryName)
        // when
        viewModel.saveCategory()

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success

        coVerify(exactly = 1) {
            createCategoryUseCase.invoke(
                CreateCategoryParameters(
                    categoryId = null,
                    name = givenCategoryName,
                )
            )
        }
        coVerify(exactly = 1) { onButtonSubmittedActionMock.invoke() }
    }

    @Test
    fun shouldOpenScreenInUpdateMode() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary()
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))

        // when
        viewModel.initCategoryFormScreenExt(
            existingCategoryId = givenCategory.categoryId.id,
        )

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        viewModel.exportedCategoryFormUiState.value.run {
            categoryName shouldBe givenCategory.categoryName
            isFormInvalid shouldBe false
            submitButtonState shouldBe CategorySubmitButton.ENABLED
            showCategoryAlreadyExistsWarning shouldBe false
            errorModalState shouldBe ErrorModalState.NotVisible
            buttonLabelKey shouldBe R.string.button_updateCategory
        }
    }

    @Test
    fun shouldShowErrorModalWhenUnableToUpdateCategory() = runTest {
        // given
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        coEvery { updateCategoryUseCase.invoke(any()) }.throws(RuntimeException("XDDD"))
        val givenCategory = randomCategoryQuickSummary()
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))

        viewModel.initCategoryFormScreenExt(
            existingCategoryId = givenCategory.categoryId.id,
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )
        // when
        viewModel.saveCategory()

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        viewModel.exportedCategoryFormUiState.value.run {
            errorModalState shouldBe ErrorModalState.Visible(R.string.error_unable_to_updateCategory)
        }
        coVerify(exactly = 0) { onButtonSubmittedActionMock.invoke() }

        // and when
        viewModel.closeErrorModal()

        // then
        viewModel.exportedCategoryFormUiState.value.run {
            errorModalState shouldBe ErrorModalState.NotVisible
        }
    }

    @Test
    fun shouldShowMessageAboutExistingCategory() = runTest {
        // given
        val givenCategory = randomCategoryQuickSummary()
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))

        viewModel.initCategoryFormScreenExt(
            existingCategoryId = givenCategory.categoryId.id,
        )
        // when
        viewModel.updateCategoryFormName(givenCategory.categoryName)

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        viewModel.exportedCategoryFormUiState.value.run {
            showCategoryAlreadyExistsWarning shouldBe true
        }

        // and
        viewModel.updateCategoryFormName("some another category")

        // then
        viewModel.exportedCategoryFormUiState.value.run {
            showCategoryAlreadyExistsWarning shouldBe false
        }
    }

    @Test
    fun shouldUpdateCategory() = runTest {
        // given
        val onButtonSubmittedActionMock = mockk<() -> Unit>()
        val givenCategory = randomCategoryQuickSummary(categoryName = "old category name")
        val givenNewCategoryName = "new category name"
        testGetCategoriesQuickSummaryUseCase.willReturnCategories(listOf(givenCategory))

        viewModel.initCategoryFormScreenExt(
            existingCategoryId = givenCategory.categoryId.id,
            onButtonSubmittedAction = onButtonSubmittedActionMock,
        )
        viewModel.updateCategoryFormName(givenNewCategoryName)
        // when
        viewModel.saveCategory()

        // expect
        viewModel.exportedCategoryScreenFormState.value is CategoryScreenFormState.Success
        coVerify(exactly = 1) {
            updateCategoryUseCase.invoke(
                Category(
                    id = givenCategory.categoryId,
                    name = givenNewCategoryName,
                )
            )
        }
        coVerify(exactly = 1) { onButtonSubmittedActionMock.invoke() }
    }

}


fun CategoryScreenFormViewModel.initCategoryFormScreenExt(
    existingCategoryId: String? = null,
    onButtonSubmittedAction: () -> Unit = {},
) {
    initCategoryFormScreen(
        existingCategoryId = existingCategoryId,
        onButtonSubmittedAction = onButtonSubmittedAction,
    )
}
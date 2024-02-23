package com.mateuszcholyn.wallet.frontend.view.screen.categoryForm

import androidx.annotation.StringRes
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategoryFormUiState
import com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen.CategorySubmitButton
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CategoryScreenFormState {
    data object Loading : CategoryScreenFormState()
    data class Success(val categoryNames: List<String>) : CategoryScreenFormState()
    data class Error(
        @StringRes
        val errorMessageKey: Int,
    ) : CategoryScreenFormState()
}


sealed interface CategoryScreenMode {
    data object Add : CategoryScreenMode
    data class Update(val categoryId: CategoryId) : CategoryScreenMode
}

@HiltViewModel
class CategoryScreenFormViewModel @Inject constructor(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) : ViewModel() { // done tests XD

    private lateinit var onButtonSubmittedAction: () -> Unit
    private var categoryScreenMode: CategoryScreenMode = CategoryScreenMode.Add

    var exportedCategoryScreenFormState = mutableStateOf<CategoryScreenFormState>(CategoryScreenFormState.Loading)
        private set
    private var categoryScreenFormState by MutableStateDelegate(exportedCategoryScreenFormState)

    var exportedCategoryFormUiState = mutableStateOf(CategoryFormUiState())
        private set
    private var categoryFormUiState by MutableStateDelegate(exportedCategoryFormUiState)


    fun initCategoryFormScreen(
        existingCategoryId: String? = null,
        onButtonSubmittedAction: () -> Unit,
    ) {
        this.onButtonSubmittedAction = onButtonSubmittedAction
        viewModelScope.launch {
            try {
                val categoryQuickSummary = getCategoriesQuickSummaryUseCase.invoke().quickSummaries
                when (existingCategoryId == null) {
                    true -> {
                        categoryScreenMode = CategoryScreenMode.Add
                        categoryFormUiState =
                            categoryFormUiState.copy(
                                buttonLabelKey = R.string.button_addCategory,
                                submitButtonState = CategorySubmitButton.DISABLED,
                            )
                    }

                    false -> {
                        categoryScreenMode = CategoryScreenMode.Update(CategoryId(existingCategoryId))
                        categoryFormUiState =
                            categoryFormUiState.copy(
                                buttonLabelKey = R.string.button_updateCategory,
                                categoryName = categoryQuickSummary.find { it.categoryId == CategoryId(existingCategoryId) }!!.categoryName,
                                submitButtonState = CategorySubmitButton.ENABLED,
                            )
                    }
                }

                categoryScreenFormState = CategoryScreenFormState.Success(categoryQuickSummary.map { it.categoryName })
            } catch (t: Throwable) {
                categoryScreenFormState = CategoryScreenFormState.Error(R.string.error_unable_to_load_category_form)

            }
        }
    }

    fun updateCategoryFormName(categoryNameFromForm: String) {
        val categoryIsInvalid = categoryIsInvalid(categoryNameFromForm)

        val submitButtonState =
            if (!categoryIsInvalid) {
                CategorySubmitButton.ENABLED
            } else {
                CategorySubmitButton.DISABLED
            }

        categoryFormUiState =
            categoryFormUiState.copy(
                categoryName = categoryNameFromForm,
                isFormInvalid = categoryIsInvalid,
                submitButtonState = submitButtonState,
            )

        val xdState = categoryScreenFormState
        if (xdState is CategoryScreenFormState.Success) {
            val shouldShowWarning = shouldShowCategoryWarning(categoryNameFromForm, xdState.categoryNames)
            categoryFormUiState =
                categoryFormUiState.copy(
                    showCategoryAlreadyExistsWarning = shouldShowWarning,
                )
        }
    }

    fun saveCategory() {
        categoryFormUiState =
            categoryFormUiState.copy(
                submitButtonState = CategorySubmitButton.LOADING,
            )


        when (val xd = categoryScreenMode) {
            CategoryScreenMode.Add -> {
                addCategory()
            }

            is CategoryScreenMode.Update -> {
                updateCategory(xd.categoryId)
            }
        }
    }

    private fun addCategory() {
        userInputAction(errorMessageKey = R.string.error_unable_to_addCategory) {
            val createCategoryParameters =
                CreateCategoryParameters(
                    name = categoryFormUiState.categoryName,
                )
            createCategoryUseCase.invoke(createCategoryParameters)
        }
    }

    private fun updateCategory(categoryId: CategoryId) {
        userInputAction(errorMessageKey = R.string.error_unable_to_updateCategory) {
            val updatedCategory =
                Category(
                    id = categoryId,
                    name = categoryFormUiState.categoryName,
                )
            updateCategoryUseCase.invoke(updatedCategory)
        }
    }


    private fun userInputAction(
        @StringRes
        errorMessageKey: Int,
        userAction: suspend () -> Unit,
    ) {
        viewModelScope.launch {// DONE
            try {
                userAction()
                onButtonSubmittedAction.invoke()
            } catch (e: Exception) {
                categoryFormUiState =
                    categoryFormUiState.copy(
                        errorModalState = ErrorModalState.Visible(errorMessageKey),
                        submitButtonState = CategorySubmitButton.ENABLED
                    )
            }
        }
    }

    fun closeErrorModal() {
        categoryFormUiState = categoryFormUiState.copy(errorModalState = ErrorModalState.NotVisible)
    }

}


private fun shouldShowCategoryWarning(category: String, categories: List<String>): Boolean =
    category != "" && category in categories

private fun categoryIsInvalid(category: String): Boolean =
    category.isBlank()
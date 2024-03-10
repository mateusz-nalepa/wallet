package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.AbstractCategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.MainCategoryQuickSummary
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.composables.delegat.MutableStateDelegate
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ErrorModalState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CategoryScreenState {
    data object Loading : CategoryScreenState()
    data class Success(val categorySuccessContent: CategorySuccessContent) : CategoryScreenState()
    data class Error(val errorMessageKey: Int) : CategoryScreenState()
}

data class CategorySuccessContent(
    val categoriesList: List<MainCategoryQuickSummary>,
)


data class RemoveCategoryState(
    val categoryRemoveConfirmationDialogIsVisible: Boolean = false,
    val errorModalState: ErrorModalState = ErrorModalState.NotVisible,
)

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) : ViewModel() { // done tests XD

    var exportedCategoryScreenState =
        mutableStateOf<CategoryScreenState>(CategoryScreenState.Loading)
        private set
    private var categoryScreenState by MutableStateDelegate(exportedCategoryScreenState)

    var exportedRemoveCategoryState = mutableStateOf(RemoveCategoryState())
        private set
    private var removeCategoryState by MutableStateDelegate(exportedRemoveCategoryState)

    fun removeCategory(abstractCategoryQuickSummary: AbstractCategoryQuickSummary) {
        viewModelScope.launch { // DONE
            try {
                unsafeRemoveCategory(abstractCategoryQuickSummary)
            } catch (e: Exception) {
                removeCategoryState =
                    removeCategoryState.copy(
                        errorModalState = ErrorModalState.Visible(R.string.error_unable_to_remove_category)
                    )
            }
        }
    }

    private suspend fun unsafeRemoveCategory(abstractCategoryQuickSummary: AbstractCategoryQuickSummary) {
        if (abstractCategoryQuickSummary.numberOfExpenses == 0L) {
            removeCategoryUseCase.invoke(abstractCategoryQuickSummary.id)
            refreshScreen()
        } else {
            removeCategoryState =
                removeCategoryState.copy(
                    errorModalState = ErrorModalState.Visible(R.string.error_unable_to_remove_category_where_are_expenses)
                )
        }
    }

    fun onRemoveCategoryModalOpen() {
        removeCategoryState =
            removeCategoryState.copy(
                categoryRemoveConfirmationDialogIsVisible = true
            )
    }

    fun onRemoveCategoryModalClose() {
        removeCategoryState =
            removeCategoryState.copy(
                categoryRemoveConfirmationDialogIsVisible = false
            )
    }

    fun closeErrorModal() {
        removeCategoryState =
            removeCategoryState.copy(
                errorModalState = ErrorModalState.NotVisible
            )
    }

    fun refreshScreen() {
        viewModelScope.launch { // DONE
            try {
                categoryScreenState = CategoryScreenState.Loading
                categoryScreenState = CategoryScreenState.Success(prepareCategorySuccessContent())
            } catch (e: Exception) {
                categoryScreenState =
                    CategoryScreenState.Error(R.string.error_unable_to_load_categories)
            }
        }
    }

    private suspend fun prepareCategorySuccessContent(): CategorySuccessContent {
        val quickSummaries = getCategoriesQuickSummaryUseCase.invokeV2().quickSummaries

        return CategorySuccessContent(
            categoriesList = quickSummaries,
        )
    }

}

package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
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
    data class Error(val errorMessage: String) : CategoryScreenState()
}

data class CategorySuccessContent(
    val categoriesList: List<CategoryQuickSummary>,
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

    var exportedCategoryScreenState = mutableStateOf<CategoryScreenState>(CategoryScreenState.Loading)
        private set
    private var categoryScreenState by MutableStateDelegate(exportedCategoryScreenState)

    var exportedRemoveCategoryState = mutableStateOf(RemoveCategoryState())
        private set
    private var removeCategoryState by MutableStateDelegate(exportedRemoveCategoryState)

    fun removeCategory(categoryId: CategoryId) {
        viewModelScope.launch { // DONE
            try {
                unsafeRemoveCategory(categoryId)
            } catch (e: Exception) {
                removeCategoryState =
                    removeCategoryState.copy(
                        errorModalState = ErrorModalState.Visible("error podczas usuwania")
                    )
            }
        }
    }

    private suspend fun unsafeRemoveCategory(categoryId: CategoryId) {
        val screenState = categoryScreenState as CategoryScreenState.Success
        if (screenState.categorySuccessContent.categoriesList.find { it.categoryId == categoryId }!!.numberOfExpenses == 0L) {
            removeCategoryUseCase.invoke(categoryId)
            refreshScreen()
        } else {
            removeCategoryState =
                removeCategoryState.copy(
                    errorModalState = ErrorModalState.Visible("Nie możesz usunąć kategorii gdzie są wydatki")
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
        // w sumie takie coś można by wszędie dać i korytyny by działały od razu XD
        // to tak odnośnie usuwania mainThreadQueries XD
        viewModelScope.launch { // DONE
            try {
                categoryScreenState = CategoryScreenState.Loading
                categoryScreenState = CategoryScreenState.Success(prepareCategorySuccessContent())
            } catch (e: Exception) {
                categoryScreenState =
                    CategoryScreenState.Error("Unknown error sad times")
            }
        }
    }

    private suspend fun prepareCategorySuccessContent(): CategorySuccessContent {
        val quickSummaries = getCategoriesQuickSummaryUseCase.invoke().quickSummaries

        return CategorySuccessContent(
            categoriesList = quickSummaries,
        )
    }

}

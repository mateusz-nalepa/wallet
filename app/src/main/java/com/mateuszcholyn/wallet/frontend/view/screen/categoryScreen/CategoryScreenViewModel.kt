package com.mateuszcholyn.wallet.frontend.view.screen.categoryScreen

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.util.actionButton.ButtonActions
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
    val categoryNamesOnly: List<String>,
)

@HiltViewModel
class CategoryScreenViewModel @Inject constructor(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,

    ) : ViewModel() {

    private var _categoryScreenState: MutableState<CategoryScreenState> = mutableStateOf(CategoryScreenState.Loading)
    val categoryScreenState: State<CategoryScreenState>
        get() = _categoryScreenState

    init {
        refreshScreen()
    }

    fun addCategory(
        newCategoryName: String,
        buttonActions: ButtonActions,
    ) {
        userInputAction(
            buttonActions,
            "Error podczas add",
        ) {
            val createCategoryParameters =
                CreateCategoryParameters(
                    name = newCategoryName,
                )
            createCategoryUseCase.invoke(createCategoryParameters)
            refreshScreen()
        }
    }

    fun updateCategory(
        categoryQuickSummary: CategoryQuickSummary,
        buttonActions: ButtonActions,
    ) {
        userInputAction(
            buttonActions,
            "Error podczas update",
        ) {
            val updatedCategory =
                Category(
                    id = categoryQuickSummary.categoryId,
                    name = categoryQuickSummary.categoryName,
                )
            updateCategoryUseCase.invoke(updatedCategory)
        }
    }

    private fun userInputAction(
        buttonActions: ButtonActions,
        errorMessage: String,
        actionToBeExecutedBeforeRefresh: suspend () -> Unit,
    ) {
        viewModelScope.launch {// DONE
            try {
                actionToBeExecutedBeforeRefresh()
                buttonActions.onSuccessAction.invoke()
            } catch (e: Exception) {
                buttonActions.onErrorAction.invoke(errorMessage)
            }
        }
    }

    fun refreshScreen() {
        // w sumie takie coś można by wszędie dać i korytyny by działały od razu XD
        // to tak odnośnie usuwania mainThreadQueries XD
        viewModelScope.launch { // DONE
            try {
                _categoryScreenState.value = CategoryScreenState.Loading
                _categoryScreenState.value = CategoryScreenState.Success(prepareCategorySuccessContent())
            } catch (e: Exception) {
                Log.d("BK", "Exception: ${e.message}")
                _categoryScreenState.value = CategoryScreenState.Error(e.message
                    ?: "Unknown error sad times")
            }
        }
    }

    private suspend fun prepareCategorySuccessContent(): CategorySuccessContent {
        val quickSummaries = getCategoriesQuickSummaryUseCase.invoke().quickSummaries
        val categoryNamesOnly = quickSummaries.map { it.categoryName }

        return CategorySuccessContent(
            categoriesList = quickSummaries,
            categoryNamesOnly = categoryNamesOnly,
        )
    }

}

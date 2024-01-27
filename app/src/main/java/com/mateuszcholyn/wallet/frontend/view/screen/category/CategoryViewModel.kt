package com.mateuszcholyn.wallet.frontend.view.screen.category

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CategoryState {
    data object Loading : CategoryState()
    data class Success(val categorySuccessContent: CategorySuccessContent) : CategoryState()
    data class Error(val errorMessage: String) : CategoryState()
}

data class CategorySuccessContent(
    val categoriesList: List<CategoryQuickSummary>,
    val categoryNamesOnly: List<String>,
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) : ViewModel() {

    private var _categoryState: MutableState<CategoryState> = mutableStateOf(CategoryState.Loading)
    val categoryState: State<CategoryState>
        get() = _categoryState

    init {
        refreshScreen()
    }

    fun addCategory(newCategoryName: NewCategoryName) {
        val createCategoryParameters =
            CreateCategoryParameters(
                name = newCategoryName,
            )
        createCategoryUseCase.invoke(createCategoryParameters)
        refreshScreen()
    }

    fun deleteCategory(categoryId: CategoryId) {
        removeCategoryUseCase.invoke(categoryId)
        refreshScreen()
    }

    fun updateCategory(categoryQuickSummary: CategoryQuickSummary) {
        val updatedCategory =
            CategoryV2(
                id = categoryQuickSummary.categoryId,
                name = categoryQuickSummary.categoryName,
            )

        updateCategoryUseCase.invoke(updatedCategory)

        refreshScreen()
    }

    fun refreshScreen() {
        // w sumie takie coś można by wszędie dać i korytyny by działały od razu XD
        // to tak odnośnie usuwania mainThreadQueries XD
        viewModelScope.launch {
            try {
                _categoryState.value = CategoryState.Loading
                _categoryState.value = CategoryState.Success(prepareCategorySuccessContent())
            } catch (e: Exception) {
                Log.d("BK", "Exception: ${e.message}")
                _categoryState.value = CategoryState.Error(e.message ?: "Unknown error sad times")
            }
        }
    }

    private fun prepareCategorySuccessContent(): CategorySuccessContent {
        val quickSummaries = getCategoriesQuickSummaryUseCase.invoke().quickSummaries
        val categoryNamesOnly = quickSummaries.map { it.categoryName }

        return CategorySuccessContent(
            categoriesList = quickSummaries,
            categoryNamesOnly = categoryNamesOnly,
        )
    }

}

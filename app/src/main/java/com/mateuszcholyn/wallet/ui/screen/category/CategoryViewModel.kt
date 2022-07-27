package com.mateuszcholyn.wallet.ui.screen.category

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.category.ExistingCategory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed class CategoryState {
    object Loading : CategoryState()
    data class Success(val categorySuccessContent: CategorySuccessContent) : CategoryState()
    data class Error(val errorMessage: String) : CategoryState()
}

data class CategorySuccessContent(
    val categoriesList: List<CategoryDetails>,
    val categoryNamesOnly: List<String>,
)

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryService: CategoryService,
) : ViewModel() {

    private var _categoryState: MutableState<CategoryState> = mutableStateOf(CategoryState.Loading)
    val categoryState: State<CategoryState>
        get() = _categoryState

    init {
        refreshScreen()
    }

    fun addCategory(newCategory: Category) {
        categoryService.add(newCategory)
        refreshScreen()
    }

    fun deleteCategory(categoryId: Long) {
        categoryService.remove(categoryId)
        refreshScreen()
    }

    fun updateCategory(existingCategory: ExistingCategory) {
        categoryService.updateCategory(existingCategory)
        refreshScreen()
    }

    fun refreshScreen() {
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
        val categories = categoryService.getAllWithDetailsOrderByUsageDesc()
        val categoryNamesOnly = categories.map { it.name }

        return CategorySuccessContent(
            categoriesList = categories,
            categoryNamesOnly = categoryNamesOnly,
        )
    }

}

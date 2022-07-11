package com.mateuszcholyn.wallet.ui.screen.category

import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryDetails
import com.mateuszcholyn.wallet.domain.category.CategoryService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryService: CategoryService,
) : ViewModel() {

    private var _categoryOptions = mutableListOf<CategoryDetails>().toMutableStateList()
    val categoryOptions: List<CategoryDetails>
        get() = _categoryOptions

    private var _categoryNamesOnly = mutableListOf<String>().toMutableStateList()
    val categoryNamesOnly: List<String>
        get() = _categoryNamesOnly

    init {
        refreshCategoryList()
    }

    fun categoryService(): CategoryService =
        categoryService

    fun refreshCategoryList() {
        _categoryOptions.clear()
        _categoryOptions.addAll(categoryService.getAllWithDetailsOrderByUsageDesc())

        _categoryNamesOnly.clear()
        _categoryNamesOnly.addAll(_categoryOptions.map { it.name })
    }

    fun addCategory(newCategory: Category) {
        categoryService.add(newCategory)
        refreshCategoryList()
    }

}

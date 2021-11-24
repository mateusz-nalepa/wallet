package com.mateuszcholyn.wallet.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryService

class CategoryViewModel(
    val categoryService: CategoryService,

    ) : ViewModel() {

    val categoryList = MutableLiveData<List<SingleCategoryV2Model>>()
    var categoryName = MutableLiveData<String>()

    init {
        setActualCategories()
    }

    fun addCategory() {
        categoryService.add(Category(name = categoryName.value!!))
        setActualCategories()
    }


    private fun setActualCategories() {
        categoryList.value =
            categoryService
                .getAllOrderByUsageDesc()
                .map { SingleCategoryV2Model(it.name) }
    }

}
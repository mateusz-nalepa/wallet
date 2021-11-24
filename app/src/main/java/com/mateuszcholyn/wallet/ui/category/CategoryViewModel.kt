package com.mateuszcholyn.wallet.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService

class CategoryViewModel(
    val categoryService: CategoryService,

    ) : ViewModel() {

    val categoryList = MutableLiveData<List<SingleCategoryV2Model>>()
    var categoryName = MutableLiveData<String>()

    init {
        categoryList.value = listOf(
            SingleCategoryV2Model("1"),
            SingleCategoryV2Model("2"),
            SingleCategoryV2Model("3"),
        )
    }

    fun addCategory() {
        println("Add category clicked!")
        println(categoryName.value)
    }

}
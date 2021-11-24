package com.mateuszcholyn.wallet.ui.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService

class CategoryViewModel(
    private val categoryService: CategoryService,

    ) : ViewModel() {

    var categoryName = MutableLiveData<String>()

    fun addCategory() {
        println("Add category clicked!")
        println(categoryName.value)
    }

}
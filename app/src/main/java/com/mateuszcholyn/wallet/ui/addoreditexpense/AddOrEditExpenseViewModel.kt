package com.mateuszcholyn.wallet.ui.addoreditexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.view.expense.ALL_CATEGORIES

class AddOrEditExpenseViewModel(
    private val expenseService: ExpenseService,
    private val categoryService: CategoryService,
) : ViewModel() {

    val categoryList = MutableLiveData<List<String>>()
    var actualCategoryPosition: Int = 0

    init {
        categoryList.value = categoryService.getAllOrderByUsageDesc().map { it.name }
    }

    fun addOrEditExpense() {
        println("Dodaj lub edytuj wydatek!")
        println("Kategoria: ${getActualCategoryName()}")
    }

    private fun isAllCategories(): Boolean {
        return getActualCategoryName() == ALL_CATEGORIES
    }

    private fun getActualCategoryName(): String {
        return categoryList.value!![actualCategoryPosition]
    }

}
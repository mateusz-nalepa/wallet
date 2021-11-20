package com.mateuszcholyn.wallet.ui.addoreditexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.mateuszcholyn.wallet.view.showShortText
import java.time.LocalDateTime

class AddOrEditExpenseViewModel(
    private val expenseService: ExpenseService,
    private val categoryService: CategoryService,
) : ViewModel() {

    var onExpenseAddedAction: () -> Unit = {}

    val categoryList = MutableLiveData<List<String>>()
    var actualCategoryPosition: Int = 0
    var date = MutableLiveData<String>()
    var amount = MutableLiveData<String>()
    var description = MutableLiveData<String>()

    init {
        date.value = LocalDateTime.now().toHumanText()
        categoryList.value = categoryService.getAllOrderByUsageDesc().map { it.name }
    }

    fun addOrEditExpense() {
        println("Dodaj lub edytuj wydatek!")
        println("Kategoria: ${getActualCategoryName()}")
        println("Date: ${date.value!!.toLocalDateTime()}")
        println("Kwota: ${amount.value}")
        println("Opis: ${description.value}")
        if (validationIncorrect()) {
            showShortText("Kwota jest niepoprawna!")
            return
        }

        onExpenseAddedAction.invoke()
    }

    private fun getActualCategoryName(): String {
        return categoryList.value!![actualCategoryPosition]
    }

    private fun validationIncorrect(): Boolean {
        val amountString = amount.value ?: ""
        return amountString == "" || amountString.startsWith(".") || amountString.startsWith("-")
    }

}
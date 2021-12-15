package com.mateuszcholyn.wallet.ui.summary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.*
import com.mateuszcholyn.wallet.view.QuickRangeV2
import java.time.LocalDateTime


val sortingTypes = listOf(
        "data: od najmłodszych",
        "data: od najstarszych",
        "cena: od najwyższej",
        "cena: od najwyższej",
)


class SummaryViewModel(
        private val categoryService: CategoryService,
        val expenseService: ExpenseService,

        ) : ViewModel() {

    val expenses = MutableLiveData<List<SummaryAdapterModel>>()

    val categoryList = MutableLiveData<List<String>>()
    val quickRangeEntries = MutableLiveData<List<String>>()
    var actualCategoryPosition: Int = 0

    val sortingList = MutableLiveData<List<String>>()
    var actualSortPosition: Int = 0

    var beginDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()

    var beginAmount = MutableLiveData<String>()
    var endAmount = MutableLiveData<String>()

    var summaryResultText = MutableLiveData<String>()
    var numberOfExpenses = MutableLiveData<String>()
    var showAddExpenseFragmentFunction: () -> Unit = {}

    init {
        beginDate.value = LocalDateTime.now().atStartOfTheDay().toHumanText()
        endDate.value = LocalDateTime.now().toHumanText()
        categoryList.value =
                listOf(ALL_CATEGORIES) + categoryService.getAllOrderByUsageDesc().map { it.name }

        sortingList.value = sortingTypes

        quickRangeEntries.value = QuickRangeV2.quickRangesNames()

        showSummary()
    }

    fun showSummary() {
        showAverageAmount()
        showHistory()
    }

    private fun showAverageAmount() {
        val result = expenseService.averageExpense(getExpenseSearchCriteria())

        summaryResultText.value =
                """
            ${result.wholeAmount.asPrinteableAmount()} zł / ${result.days} d = ${result.averageAmount.asPrinteableAmount()} zł/d
        """.trimIndent()
    }

    private fun showHistory() {
        expenses.value =
                expenseService
                        .getAll(getExpenseSearchCriteria())
                        .also { numberOfExpenses.value = "Ilość wydatków: ${it.size}" }
                        .map {
                            SummaryAdapterModel(
                                    it.id,
                                    it.description,
                                    it.date.toHumanText(),
                                    it.amount.asPrinteableAmount().toString(),
                                    it.category.name,
                            )
                        }
    }

    private fun getExpenseSearchCriteria(): ExpenseSearchCriteria {

        val fromAmount =
                if (beginAmount.value == null || beginAmount.value == "") {
                    Int.MIN_VALUE.toDouble()
                } else {
                    beginAmount.value!!.toDouble()
                }

        val toAmount =
                if (endAmount.value == null || endAmount.value == "") {
                    Int.MAX_VALUE.toDouble()
                } else {
                    endAmount.value!!.toDouble()
                }

        println("sortingType: ${sortingTypes[actualSortPosition]}")

        return ExpenseSearchCriteria(
                allCategories = isAllCategories(),
                categoryName = if (getActualCategoryName() == ALL_CATEGORIES) null else getActualCategoryName(),
                beginDate = beginDate.value!!.toLocalDateTime(),
                endDate = endDate.value!!.toLocalDateTime(),
                fromAmount = fromAmount,
                toAmount = toAmount,
        )
    }

    private fun isAllCategories(): Boolean {
        return getActualCategoryName() == ALL_CATEGORIES
    }

    private fun getActualCategoryName(): String {
        return categoryList.value!![actualCategoryPosition]
    }

    fun showAddExpenseFragment() {
        showAddExpenseFragmentFunction.invoke()
    }

}
package com.mateuszcholyn.wallet.ui.summary

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.ALL_CATEGORIES
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.mateuszcholyn.wallet.view.QuickRangeV2
import java.time.LocalDateTime

class SummaryViewModel(
    private val categoryService: CategoryService,
    val expenseService: ExpenseService,

    ) : ViewModel() {

    val expenses = MutableLiveData<List<SummaryAdapterModel>>()

    val categoryList = MutableLiveData<List<String>>()
    val quickRangeEntries = MutableLiveData<List<String>>()
    var actualCategoryPosition: Int = 0
    var beginDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()
    var summaryResultText = MutableLiveData<String>()
    var showAddExpenseFragmentFunction: () -> Unit = {}

    init {
        beginDate.value = LocalDateTime.now().atStartOfTheDay().toHumanText()
        endDate.value = LocalDateTime.now().toHumanText()
        categoryList.value =
            listOf(ALL_CATEGORIES) + categoryService.getAllOrderByUsageDesc().map { it.name }
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
        return ExpenseSearchCriteria(
            allCategories = isAllCategories(),
            categoryName = if (getActualCategoryName() == ALL_CATEGORIES) null else getActualCategoryName(),
            beginDate = beginDate.value!!.toLocalDateTime(),
            endDate = endDate.value!!.toLocalDateTime()
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
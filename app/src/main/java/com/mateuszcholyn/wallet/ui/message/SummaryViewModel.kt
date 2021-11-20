package com.mateuszcholyn.wallet.ui.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.util.toLocalDateTime
import com.mateuszcholyn.wallet.view.QuickRangeV2
import com.mateuszcholyn.wallet.view.expense.ALL_CATEGORIES
import java.time.LocalDateTime

class SummaryViewModel(
    private val categoryService: CategoryService,
    private val expenseService: ExpenseService,

    ) : ViewModel() {
    val categoryList = MutableLiveData<List<String>>()
    val quickRangeEntries = MutableLiveData<List<String>>()
    var actualCategoryPosition: Int = 0
    var beginDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()
    var summaryResultText = MutableLiveData<String>()

    init {
        beginDate.value = LocalDateTime.now().atStartOfTheDay().toHumanText()
        endDate.value = LocalDateTime.now().toHumanText()
        categoryList.value =
            listOf(ALL_CATEGORIES) + categoryService.getAllOrderByUsageDesc().map { it.name }
        quickRangeEntries.value = QuickRangeV2.quickRangesNames()

        calculateAverageAmount()
    }

    fun calculateAverageAmount() {
        summaryResultText.value = calculate()
    }

    private fun calculate(): String {
        val result =
            expenseService.averageExpense(
                ExpenseSearchCriteria(
                    allCategories = isAllCategories(),
                    categoryName = if (getActualCategoryName() == ALL_CATEGORIES) null else getActualCategoryName(),
                    beginDate = beginDate.value!!.toLocalDateTime(),
                    endDate = endDate.value!!.toLocalDateTime()
                )
            )

        return """
            ${result.wholeAmount.asPrinteableAmount()} zł / ${result.days} d = ${result.averageAmount.asPrinteableAmount()} zł/d
        """.trimIndent()
    }

    private fun isAllCategories(): Boolean {
        return getActualCategoryName() == ALL_CATEGORIES
    }

    private fun getActualCategoryName(): String {
        return categoryList.value!![actualCategoryPosition]
    }

}
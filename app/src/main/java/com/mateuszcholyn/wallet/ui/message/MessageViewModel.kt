package com.mateuszcholyn.wallet.ui.message

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import com.mateuszcholyn.wallet.util.oneWeekAgo
import com.mateuszcholyn.wallet.util.toHumanText
import com.mateuszcholyn.wallet.view.QuickRangeV2
import java.time.LocalDateTime

class MessageViewModel(
    private val categoryService: CategoryService,
    private val expenseService: ExpenseService,

    ) : ViewModel() {
    val categoryList = MutableLiveData<List<String>>()
    val quickRangeEntries = MutableLiveData<List<String>>()
    var actualCategoryPosition: Int = 0
    var beginDate = MutableLiveData<String>()
    var endDate = MutableLiveData<String>()

    init {
        beginDate.value = LocalDateTime.now().atStartOfTheDay().toHumanText()
        endDate.value = LocalDateTime.now().toHumanText()
        categoryList.value = categoryService.getAllOrderByUsageDesc().map { it.name }
        quickRangeEntries.value = QuickRangeV2.quickRangesNames()
    }


    fun calculateAverageAmount() {
        println("Button clicked!")
        println("Actual category position: $actualCategoryPosition")
        println("Actual beginDate: ${beginDate.value}")
        println("Actual endDate: ${endDate.value}")
    }

    private fun calculate(): String {
        val result =
            expenseService.averageExpense(
                ExpenseSearchCriteria(
                    allCategories = true,
                    beginDate = oneWeekAgo(),
                    endDate = LocalDateTime.now()
                )
            )

        return """
            ${(1..100).random()}
            XD W ciagu ostatnich 7 dni wydales: ${result.wholeAmount.asPrinteableAmount()} zł,
            czyli srednio na dzien: ${result.averageAmount.asPrinteableAmount()} zł
        """.trimIndent()
    }

}
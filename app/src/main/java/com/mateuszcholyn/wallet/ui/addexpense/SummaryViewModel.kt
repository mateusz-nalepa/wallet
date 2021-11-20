package com.mateuszcholyn.wallet.ui.addexpense

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

//class SummaryViewModel(private val expenseService: ExpenseService) : ViewModel() {
class SummaryViewModel() : ViewModel() {
    val textSummaryLiveData = MutableLiveData<String>()

    init {
        setActualValue()
    }

    fun setActualValue() {
        textSummaryLiveData.value = getText()
    }

    private fun getText() = (1..100).random().toString()

//    private fun getText(): String {
//        val result =
//            expenseService.averageExpense(
//                ExpenseSearchCriteria(
//                    allCategories = true,
//                    beginDate = oneWeekAgo(),
//                    endDate = LocalDateTime.now()
//                )
//            )
//
//        return """
//            XD W ciagu ostatnich 7 dni wydales: ${result.wholeAmount.asPrinteableAmount()} zł,
//            czyli srednio na dzien: ${result.averageAmount.asPrinteableAmount()} zł
//        """.trimIndent()
//    }

}
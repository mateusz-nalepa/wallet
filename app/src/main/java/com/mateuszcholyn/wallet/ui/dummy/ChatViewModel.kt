package com.mateuszcholyn.wallet.ui.dummy

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.oneWeekAgo
import java.time.LocalDateTime

class ChatViewModel(private val expenseService: ExpenseService) : ViewModel() {
    private val textChatLiveData = MutableLiveData<String>()

    val textChat: String
        get() = textChatLiveData.value!!

    init {
        setActualValue()
    }

    fun setActualValue() {
        textChatLiveData.value = calculate()
    }

    private fun calculate(): String {
        val result =
                expenseService.averageExpense(
                        ExpenseSearchCriteria(
                                allCategories = true,
                                beginDate = oneWeekAgo(),
                                endDate = LocalDateTime.now(),
                                fromAmount = Int.MIN_VALUE.toDouble(),
                                toAmount = Int.MAX_VALUE.toDouble(),
                        )
                )

        return """
            ${(1..100).random()}
            XD W ciagu ostatnich 7 dni wydales: ${result.wholeAmount.asPrinteableAmount()} zł,
            czyli srednio na dzien: ${result.averageAmount.asPrinteableAmount()} zł
        """.trimIndent()
    }

}
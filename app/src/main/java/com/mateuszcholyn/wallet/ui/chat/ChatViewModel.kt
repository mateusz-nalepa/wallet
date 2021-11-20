package com.mateuszcholyn.wallet.ui.chat

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import com.mateuszcholyn.wallet.util.oneWeekAgo
import java.time.LocalDateTime

class ChatViewModel(private val expenseService: ExpenseService) : ViewModel() {
    val textChatLiveData = MutableLiveData<String>()

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
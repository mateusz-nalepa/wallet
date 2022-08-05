package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import java.math.BigDecimal


interface SearchServiceAPI {
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun getAll(searchCriteria: SearchCriteria): SearchServiceResult
}

data class SearchServiceResult(
    val expenses: List<ExpenseAddedEvent>,
    val averageExpenseResult: SearchAverageExpenseResult,
)

data class SearchAverageExpenseResult(
    val wholeAmount: BigDecimal,
    val days: Int,
    val averageAmount: BigDecimal,
)

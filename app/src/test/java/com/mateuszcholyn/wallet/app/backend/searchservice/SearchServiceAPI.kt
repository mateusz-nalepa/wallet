package com.mateuszcholyn.wallet.app.backend.searchservice

import com.mateuszcholyn.wallet.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.app.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.app.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.app.backend.events.ExpenseUpdatedEvent
import java.math.BigDecimal
import java.time.LocalDateTime


data class SearchCriteria(
    val categoryId: CategoryId? = null,
    val beginDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val fromAmount: BigDecimal? = null,
    val toAmount: BigDecimal? = null,
    val sort: NewSort = NewSort(NewSort.Field.DATE, NewSort.Type.DESC)
)

interface SearchServiceAPI {
    fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
    fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent)
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

data class NewSort(
    val field: Field,
    val type: Type,
) {

    enum class Field {
        DATE,
        AMOUNT,
    }

    enum class Type {
        ASC,
        DESC,
    }
}

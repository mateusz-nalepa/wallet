package com.mateuszcholyn.wallet.backend.api.searchservice

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent
import java.math.BigDecimal
import java.time.LocalDateTime


data class SearchCriteria(
    val categoryId: CategoryId? = null,
    val beginDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val fromAmount: BigDecimal? = null,
    val toAmount: BigDecimal? = null,
    val sort: NewSort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC)
)

interface SearchServiceAPI {
    fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
    fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent)
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun getAll(searchCriteria: SearchCriteria): SearchServiceResult
    fun removeAll()
}

data class SearchServiceResult(
    val expenses: List<SearchSingleResult>,
    val averageExpenseResult: SearchAverageExpenseResult,
)


data class SearchSingleResult(
    val expenseId: ExpenseId,
    val categoryId: CategoryId,
    val categoryName: String,
    val amount: BigDecimal,
    val paidAt: LocalDateTime,
    val description: String,
)

data class SearchAverageExpenseResult(
    val wholeAmount: BigDecimal,
    val days: Int,
    val averageAmount: BigDecimal,
)

data class NewSort(
    val field: Field,
    val order: Order,
) {

    enum class Field {
        DATE,
        AMOUNT,
    }

    enum class Order {
        ASC,
        DESC,
    }
}
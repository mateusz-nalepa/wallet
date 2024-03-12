package com.mateuszcholyn.wallet.backend.api.searchservice

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseUpdatedEvent
import java.math.BigDecimal
import java.time.Instant


data class SearchCriteria(
    val categoryId: CategoryId? = null,
    val subCategoryId: SubCategoryId? = null,
    val beginDate: Instant? = null,
    val endDate: Instant? = null,
    val fromAmount: BigDecimal? = null,
    val toAmount: BigDecimal? = null,
    val sort: NewSort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC)
)

interface SearchServiceAPI {
    suspend fun handleEventExpenseRemoved(expenseRemovedEvent: ExpenseRemovedEvent)
    suspend fun handleEventExpenseUpdated(expenseUpdatedEvent: ExpenseUpdatedEvent)
    suspend fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    suspend fun getAll(searchCriteria: SearchCriteria): SearchServiceResult
    suspend fun removeAll()
}

data class SearchServiceResult(
    val expenses: List<SearchSingleResult>,
    val averageExpenseResult: SearchAverageExpenseResult,
)


data class SearchSingleResult(
    val expenseId: ExpenseId,

    val categoryId: CategoryId,
    val categoryName: String,
    val subCategoryId: SubCategoryId?,
    val subCategoryName: String?,

    val amount: BigDecimal,
    val paidAt: Instant,
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

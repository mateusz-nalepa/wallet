package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.domain.expense.Sort
import java.math.BigDecimal
import java.time.LocalDateTime


//val allCategories: Boolean,
//val categoryId: Long? = null,
//val beginDate: LocalDateTime,
//val endDate: LocalDateTime,


//val fromAmount: Double = Double.MIN_VALUE,
//val toAmount: Double = Double.MAX_VALUE,
//val isAllExpenses: Boolean = false,
val sort: Sort = Sort(Sort.Field.DATE, Sort.Type.DESC)

data class SearchCriteria(
    val categoryId: CategoryId? = null,
    val beginDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
    val fromAmount: BigDecimal? = null,
    val toAmount: BigDecimal? = null,
    val sort: Sort = Sort(Sort.Field.DATE, Sort.Type.DESC)
)

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

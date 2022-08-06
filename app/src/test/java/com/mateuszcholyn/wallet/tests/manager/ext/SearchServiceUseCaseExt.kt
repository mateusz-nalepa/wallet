package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.searchservice.NewSort
import com.mateuszcholyn.wallet.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.math.BigDecimal
import java.time.LocalDateTime

fun ExpenseAppManager.searchServiceUseCase(
    initBlock: SearchCriteriaScope.() -> Unit,
): SearchServiceResult {

    val searchCriteria =
        SearchCriteriaScope()
            .also(initBlock)
            .toSearchCriteria()

    return this
        .expenseAppUseCases
        .searchServiceUseCase
        .invoke(searchCriteria)
}

class SearchCriteriaScope {
    var beginDate: LocalDateTime? = null
    var endDate: LocalDateTime? = null
    var categoryId: CategoryId? = null
    var fromAmount: BigDecimal? = null
    var toAmount: BigDecimal? = null
    var sort: NewSort = NewSort(NewSort.Field.DATE, NewSort.Type.DESC)

    fun toSearchCriteria(): SearchCriteria =
        SearchCriteria(
            categoryId = categoryId,
            beginDate = beginDate,
            endDate = endDate,
            fromAmount = fromAmount,
            toAmount = toAmount,
            sort = sort,
        )

}

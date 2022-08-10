package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.NewSort
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceResult
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
    var sort: NewSort = NewSort(NewSort.Field.DATE, NewSort.Order.DESC)

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

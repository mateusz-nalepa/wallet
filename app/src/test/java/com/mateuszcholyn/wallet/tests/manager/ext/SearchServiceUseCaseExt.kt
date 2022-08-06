package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
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
    var allCategories: Boolean? = null
    var categoryId: CategoryId? = null


    fun toSearchCriteria(): SearchCriteria =
        SearchCriteria(
            allCategories = allCategories,
            categoryId = categoryId,
            beginDate = beginDate,
            endDate = endDate,
        )

}

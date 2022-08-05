package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.util.dateutils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.dateutils.atStartOfTheDay
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
    var beginDate: LocalDateTime = LocalDateTime.now().atStartOfTheDay()
    var endDate: LocalDateTime = LocalDateTime.now().atEndOfTheDay()
    var allCategories: Boolean = true
    var categoryId: CategoryId? = null


    fun toSearchCriteria(): SearchCriteria =
        SearchCriteria(
            allCategories = allCategories,
            categoryId = categoryId,
            beginDate = beginDate,
            endDate = endDate,
        )


}

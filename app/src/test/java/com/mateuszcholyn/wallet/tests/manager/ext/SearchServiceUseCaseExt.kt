package com.mateuszcholyn.wallet.tests.manager.ext

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceResult
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppManager
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

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
    val beginDate: LocalDateTime = LocalDateTime.of(LocalDate.now(), LocalTime.MIN)
    val endDate: LocalDateTime = LocalDateTime.now().plusHours(1)
    val allCategories: Boolean = true
    val categoryId: CategoryId? = null


    fun toSearchCriteria(): SearchCriteria =
        SearchCriteria(
            allCategories = allCategories,
            categoryId = categoryId,
            beginDate = beginDate,
            endDate = endDate,
        )


}

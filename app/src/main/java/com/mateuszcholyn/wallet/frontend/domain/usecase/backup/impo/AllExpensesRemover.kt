package com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI

class AllExpensesRemover(
    private val searchServiceAPI: SearchServiceAPI,
    private val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) {

    fun removeAll() {
        searchServiceAPI.removeAll()
        categoriesQuickSummaryAPI.removeAll()
        expenseCoreServiceAPI.removeAll()
        categoryCoreServiceAPI.removeAll()
    }

}
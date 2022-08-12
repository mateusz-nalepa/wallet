package com.mateuszcholyn.wallet.newcode.app.backend

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceAPI

/**
 * Inject this class in every use case to be sure, that Hilt Setup with MiniKafka is fully working
 */

class AllBackendServices(
    val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
    val searchServiceAPI: SearchServiceAPI,
    val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) {
    init {
        println("BackendHiltIsConfigured")
    }
}
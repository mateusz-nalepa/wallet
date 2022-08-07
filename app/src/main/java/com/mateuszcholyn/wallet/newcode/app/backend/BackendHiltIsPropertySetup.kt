package com.mateuszcholyn.wallet.newcode.app.backend

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceAPI

/**
 * Inject this class in every use case to be sure, that Hilt Setup with MiniKafka is fully working
 */
interface BackendIsPropertySetup

class BackendHiltIsPropertySetup(
    private val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
    private val searchServiceAPI: SearchServiceAPI,
    private val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : BackendIsPropertySetup {
    init {
        println("BackendHiltIsPropertySetup")
    }
}
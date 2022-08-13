package com.mateuszcholyn.wallet.backend.api

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI


class AllBackendServices(
    val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
    val searchServiceAPI: SearchServiceAPI,
    val expenseCoreServiceAPI: ExpenseCoreServiceAPI,
    val categoryCoreServiceAPI: CategoryCoreServiceAPI,
)

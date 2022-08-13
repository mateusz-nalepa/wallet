package com.mateuszcholyn.wallet.app.setupintegrationtests

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.manager.ExpenseAppDependencies
import javax.inject.Inject

class ExpenseAppIntegrationManager @Inject constructor(
    // Use cases
    private val expenseAppUseCases: ExpenseAppUseCases,

    // Dependencies
    private val categoryRepositoryV2: CategoryRepositoryV2,
    private val expenseRepositoryV2: ExpenseRepositoryV2,
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
    private val searchServiceRepository: SearchServiceRepository,

    // Ensure Backend is Working by injecting this dependency
    private val allBackendServices: AllBackendServices,
) {

    fun expenseAppUseCases(): ExpenseAppUseCases =
        expenseAppUseCases

    fun dependencies(): ExpenseAppDependencies =
        ExpenseAppDependencies(
            categoryRepositoryV2 = categoryRepositoryV2,
            expenseRepositoryV2 = expenseRepositoryV2,
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            searchServiceRepository = searchServiceRepository,
        )

}

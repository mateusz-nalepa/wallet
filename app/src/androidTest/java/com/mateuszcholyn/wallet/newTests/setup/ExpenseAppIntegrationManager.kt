package com.mateuszcholyn.wallet.newTests.setup

import com.mateuszcholyn.wallet.newcode.app.backend.AllBackendServices
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppDependencies
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppUseCases
import javax.inject.Inject

class ExpenseAppIntegrationManager @Inject constructor(
    // Use cases
    private val expenseAppUseCases: ExpenseAppUseCases,

    // Dependencies
    private val categoryRepositoryV2: CategoryRepositoryV2,
    private val expenseRepositoryV2: ExpenseRepositoryV2,
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
    private val searchServiceRepository: SearchServiceRepository,

    // All App works
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

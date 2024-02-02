package com.mateuszcholyn.wallet.app.setupintegrationtests

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.manager.ExpenseAppDependencies
import javax.inject.Inject

class ExpenseAppIntegrationManager @Inject constructor(
    // Use cases
    private val expenseAppUseCases: ExpenseAppUseCases,

    // Dependencies
    private val categoryRepository: CategoryRepository,
    private val expenseRepository: ExpenseRepository,
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
    private val searchServiceRepository: SearchServiceRepository,

    // Ensure Backend is Working by injecting this dependency
    private val allBackendServices: AllBackendServices,
) {

    fun expenseAppUseCases(): ExpenseAppUseCases =
        expenseAppUseCases

    fun dependencies(): ExpenseAppDependencies =
        ExpenseAppDependencies(
            categoryRepository = categoryRepository,
            expenseRepository = expenseRepository,
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            searchServiceRepository = searchServiceRepository,
        )

}

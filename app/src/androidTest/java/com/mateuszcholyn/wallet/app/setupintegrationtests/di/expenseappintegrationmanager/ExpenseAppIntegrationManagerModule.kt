package com.mateuszcholyn.wallet.app.setupintegrationtests.di.expenseappintegrationmanager

import com.mateuszcholyn.wallet.app.setupintegrationtests.ExpenseAppIntegrationManager
import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ExpenseAppIntegrationManagerModule {

    @Provides
    @Singleton
    fun provideExpenseAppIntegrationManager(
        expenseAppUseCases: ExpenseAppUseCases,

        // Dependencies
        categoryRepository: CategoryRepository,
        expenseRepository: ExpenseRepository,
        categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
        searchServiceRepository: SearchServiceRepository,
        allBackendServices: AllBackendServices,
    ): ExpenseAppIntegrationManager =
        ExpenseAppIntegrationManager(
            // Use cases
            expenseAppUseCases = expenseAppUseCases,

            // Dependencies
            categoryRepository = categoryRepository,
            expenseRepository = expenseRepository,
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            searchServiceRepository = searchServiceRepository,

            // app works
            allBackendServices = allBackendServices,
        )

}



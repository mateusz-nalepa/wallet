package com.mateuszcholyn.wallet.app.setupintegrationtests.di.expenseappintegrationmanager

import com.mateuszcholyn.wallet.app.setupintegrationtests.ExpenseAppIntegrationManager
import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
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
        categoryRepositoryV2: CategoryRepositoryV2,
        expenseRepositoryV2: ExpenseRepositoryV2,
        categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
        searchServiceRepository: SearchServiceRepository,
        allBackendServices: AllBackendServices,
    ): ExpenseAppIntegrationManager =
        ExpenseAppIntegrationManager(
            // Use cases
            expenseAppUseCases = expenseAppUseCases,

            // Dependencies
            categoryRepositoryV2 = categoryRepositoryV2,
            expenseRepositoryV2 = expenseRepositoryV2,
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            searchServiceRepository = searchServiceRepository,

            // app works
            allBackendServices = allBackendServices,
        )

}



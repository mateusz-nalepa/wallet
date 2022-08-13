package com.mateuszcholyn.wallet.backend.impl.di.repositories

import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabaseV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.SqLiteCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.SqLiteCategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.SqLiteExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice.SqLiteSearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltProdDatabaseModuleV2 {

    @Provides
    @Singleton
    fun provideInMemoryCoreRepositoryV2(): InMemoryCoreRepositoryV2 =
        InMemoryCoreRepositoryV2()

    @Provides
    @Singleton
    fun provideCategoryRepositoryV2(
        inMemoryCoreRepositoryV2: InMemoryCoreRepositoryV2,
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
    ): CategoryRepositoryV2 =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            inMemoryCoreRepositoryV2
        } else {
            SqLiteCategoryRepositoryV2(appDatabaseV2.categoryV2Dao())
        }

    @Provides
    @Singleton
    fun provideExpenseRepositoryV2(
        inMemoryCoreRepositoryV2: InMemoryCoreRepositoryV2,
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
    ): ExpenseRepositoryV2 =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            inMemoryCoreRepositoryV2
        } else {
            SqLiteExpenseRepositoryV2(appDatabaseV2.expenseV2Dao())
        }

    @Provides
    @Singleton
    fun provideCategoriesQuickSummaryRepository(
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
    ): CategoriesQuickSummaryRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemoryCategoriesQuickSummaryRepository()
        } else {
            SqLiteCategoriesQuickSummaryRepository(appDatabaseV2.categoriesQuickSummaryDao())
        }

    @Provides
    @Singleton
    fun provideSearchServiceRepository(
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
    ): SearchServiceRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemorySearchServiceRepository()
        } else {
            SqLiteSearchServiceRepository(appDatabaseV2.searchServiceDao())
        }

}

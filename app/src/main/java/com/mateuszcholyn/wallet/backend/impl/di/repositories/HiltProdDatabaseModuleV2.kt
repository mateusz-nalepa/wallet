package com.mateuszcholyn.wallet.backend.impl.di.repositories

import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DefaultDispatcherProvider
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
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
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()

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
        dispatcherProvider: DispatcherProvider,
    ): CategoryRepositoryV2 =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            inMemoryCoreRepositoryV2
        } else {
            SqLiteCategoryRepositoryV2(
                categoryV2Dao = appDatabaseV2.categoryV2Dao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

    @Provides
    @Singleton
    fun provideExpenseRepositoryV2(
        inMemoryCoreRepositoryV2: InMemoryCoreRepositoryV2,
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): ExpenseRepositoryV2 =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            inMemoryCoreRepositoryV2
        } else {
            SqLiteExpenseRepositoryV2(
                expenseV2Dao = appDatabaseV2.expenseV2Dao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

    @Provides
    @Singleton
    fun provideCategoriesQuickSummaryRepository(
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): CategoriesQuickSummaryRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemoryCategoriesQuickSummaryRepository()
        } else {
            SqLiteCategoriesQuickSummaryRepository(
                categoriesQuickSummaryDao = appDatabaseV2.categoriesQuickSummaryDao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

    @Provides
    @Singleton
    fun provideSearchServiceRepository(
        appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): SearchServiceRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemorySearchServiceRepository()
        } else {
            SqLiteSearchServiceRepository(
                searchServiceDao = appDatabaseV2.searchServiceDao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

}

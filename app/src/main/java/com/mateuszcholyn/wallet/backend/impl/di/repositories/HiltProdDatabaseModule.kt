package com.mateuszcholyn.wallet.backend.impl.di.repositories

import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.InMemoryCoreRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DefaultDispatcherProvider
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.AppDatabase
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.SqLiteCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.SqLiteExpenseRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice.SqLiteSearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.demomode.DemoAppSwitcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltProdDatabaseModule {

    @Provides
    @Singleton
    fun provideDispatcherProvider(): DispatcherProvider =
        DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideInMemoryCoreRepository(): InMemoryCoreRepository =
        InMemoryCoreRepository()

    @Provides
    @Singleton
    fun provideCategoryRepository(
        inMemoryCoreRepository: InMemoryCoreRepository,
        appDatabase: AppDatabase,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): CategoryRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            inMemoryCoreRepository
        } else {
            SqLiteCategoryRepository(
                categoryDao = appDatabase.categoryDao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        inMemoryCoreRepository: InMemoryCoreRepository,
        appDatabase: AppDatabase,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): ExpenseRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            inMemoryCoreRepository
        } else {
            SqLiteExpenseRepository(
                expenseDao = appDatabase.expenseDao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

    @Provides
    @Singleton
    fun provideCategoriesQuickSummaryRepository(
        appDatabase: AppDatabase,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): CategoriesQuickSummaryRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemoryCategoriesQuickSummaryRepository()
        } else {
            SqLiteCategoriesQuickSummaryRepository(
                categoriesQuickSummaryDao = appDatabase.categoriesQuickSummaryDao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

    @Provides
    @Singleton
    fun provideSearchServiceRepository(
        appDatabase: AppDatabase,
        demoAppSwitcher: DemoAppSwitcher,
        dispatcherProvider: DispatcherProvider,
    ): SearchServiceRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemorySearchServiceRepository()
        } else {
            SqLiteSearchServiceRepository(
                searchServiceDao = appDatabase.searchServiceDao(),
                dispatcherProvider = dispatcherProvider,
            )
        }

}

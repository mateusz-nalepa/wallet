package com.mateuszcholyn.wallet.di.usecases

import com.mateuszcholyn.wallet.config.AppDatabaseV2
import com.mateuszcholyn.wallet.di.hilt.NewAppQualifier
import com.mateuszcholyn.wallet.domain.DemoAppSwitcher
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.SqLiteCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.SqLiteCategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.SqLiteExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
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
    fun provideInMemoryCoreRepositoryV2(): InMemoryCoreRepositoryV2 {
        return InMemoryCoreRepositoryV2()
    }

    @Provides
    @Singleton
    fun provideCategoryRepositoryV2(
        inMemoryCoreRepositoryV2: InMemoryCoreRepositoryV2,
        @NewAppQualifier appDatabaseV2: AppDatabaseV2,
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
        @NewAppQualifier appDatabaseV2: AppDatabaseV2,
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
        @NewAppQualifier appDatabaseV2: AppDatabaseV2,
        demoAppSwitcher: DemoAppSwitcher,
    ): CategoriesQuickSummaryRepository =
        if (demoAppSwitcher.isDemoModeEnabled()) {
            InMemoryCategoriesQuickSummaryRepository()
        } else {
            SqLiteCategoriesQuickSummaryRepository(appDatabaseV2.categoriesQuickSummaryDao())
        }


    @Provides
    @Singleton
    fun provideSearchServiceRepository(): SearchServiceRepository =
        InMemorySearchServiceRepository()

}

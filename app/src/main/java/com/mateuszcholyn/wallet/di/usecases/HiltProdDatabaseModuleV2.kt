package com.mateuszcholyn.wallet.di.usecases

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
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
    ): CategoryRepositoryV2 {
        return inMemoryCoreRepositoryV2
    }

    @Provides
    @Singleton
    fun provideExpenseRepositoryV2(
        inMemoryCoreRepositoryV2: InMemoryCoreRepositoryV2,
    ): ExpenseRepositoryV2 {
        return inMemoryCoreRepositoryV2
    }

    @Provides
    @Singleton
    fun provideCategoriesQuickSummaryRepository(): CategoriesQuickSummaryRepository {
        return InMemoryCategoriesQuickSummaryRepository()
    }

    @Provides
    @Singleton
    fun provideSearchServiceRepository(): SearchServiceRepository {
        return InMemorySearchServiceRepository()
    }


}

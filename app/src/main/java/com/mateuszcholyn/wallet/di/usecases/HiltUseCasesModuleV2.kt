package com.mateuszcholyn.wallet.di.usecases

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.searchservice.SearchServiceUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltUseCasesModuleV2 {

    @Provides
    @Singleton
    fun provideCreateCategoryUseCase(categoryCoreServiceAPI: CategoryCoreServiceAPI): CreateCategoryUseCase =
        CreateCategoryUseCase(
            categoryCoreService = categoryCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideUpdateCategoryUseCase(categoryCoreServiceAPI: CategoryCoreServiceAPI): UpdateCategoryUseCase =
        UpdateCategoryUseCase(
            categoryCoreService = categoryCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideRemoveCategoryUseCase(categoryCoreServiceAPI: CategoryCoreServiceAPI): RemoveCategoryUseCase =
        RemoveCategoryUseCase(
            categoryCoreService = categoryCoreServiceAPI,
        )


    @Provides
    @Singleton
    fun provideAddExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): AddExpenseUseCase =
        AddExpenseUseCase(
            expenseCoreService = expenseCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideGetExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): GetExpenseUseCase =
        GetExpenseUseCase(
            expenseCoreService = expenseCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideUpdateExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): UpdateExpenseUseCase =
        UpdateExpenseUseCase(
            expenseCoreService = expenseCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideRemoveExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): RemoveExpenseUseCase =
        RemoveExpenseUseCase(
            expenseCoreService = expenseCoreServiceAPI,
        )


    @Provides
    @Singleton
    fun provideGetCategoriesQuickSummaryUseCase(categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI): GetCategoriesQuickSummaryUseCase =
        GetCategoriesQuickSummaryUseCase(
            categoriesQuickSummary = categoriesQuickSummaryAPI,
        )


    @Provides
    @Singleton
    fun provideSearchServiceUseCase(searchServiceAPI: SearchServiceAPI): SearchServiceUseCase =
        SearchServiceUseCase(
            searchService = searchServiceAPI,
        )

}
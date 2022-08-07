package com.mateuszcholyn.wallet.newTests.di

import com.mateuszcholyn.wallet.newTests.ExpenseAppIntegrationManager
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.AddExpenseUseCase
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
class ExpenseAppIntegrationManagerModule {

    @Provides
    @Singleton
    fun provideExpenseAppIntegrationManager(
        createCategoryUseCase: CreateCategoryUseCase,
        updateCategoryUseCase: UpdateCategoryUseCase,
        removeCategoryUseCase: RemoveCategoryUseCase,
        addExpenseUseCase: AddExpenseUseCase,
        updateExpenseUseCase: UpdateExpenseUseCase,
        removeExpenseUseCase: RemoveExpenseUseCase,
        getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
        searchServiceUseCase: SearchServiceUseCase,

        // Dependencies
        categoryRepositoryV2: CategoryRepositoryV2,
        expenseRepositoryV2: ExpenseRepositoryV2,
        categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
        searchServiceRepository: SearchServiceRepository,
    ): ExpenseAppIntegrationManager =
        ExpenseAppIntegrationManager(
            // Use cases
            createCategoryUseCase = createCategoryUseCase,
            updateCategoryUseCase = updateCategoryUseCase,
            removeCategoryUseCase = removeCategoryUseCase,
            addExpenseUseCase = addExpenseUseCase,
            updateExpenseUseCase = updateExpenseUseCase,
            removeExpenseUseCase = removeExpenseUseCase,
            getCategoriesQuickSummaryUseCase = getCategoriesQuickSummaryUseCase,
            searchServiceUseCase = searchServiceUseCase,

            // Dependencies
            categoryRepositoryV2 = categoryRepositoryV2,
            expenseRepositoryV2 = expenseRepositoryV2,
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            searchServiceRepository = searchServiceRepository,
        )

}



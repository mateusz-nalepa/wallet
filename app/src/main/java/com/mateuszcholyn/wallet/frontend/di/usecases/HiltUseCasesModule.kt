package com.mateuszcholyn.wallet.frontend.di.usecases

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManager
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export.ExportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.AllExpensesRemover
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.DefaultGetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.GetCategoriesUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.DefaultAddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.DefaultGetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.DefaultUpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.HistoryToCsvFileGenerator
import com.mateuszcholyn.wallet.frontend.view.screen.history.filters.advancedOptions.exportToCsv.HistoryToCsvGenerator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import java.time.LocalDateTime
import javax.inject.Singleton

interface LocalDateTimeProvider {
    fun now(): LocalDateTime
}

class DefaultLocalDateTimeProvider : LocalDateTimeProvider {
    override fun now(): LocalDateTime =
        LocalDateTime.now()
}


@Module
@InstallIn(SingletonComponent::class)
object HiltUseCasesModule {

    @Provides
    @Singleton
    fun provideHistoryToCsvGenerator(): HistoryToCsvGenerator =
        HistoryToCsvFileGenerator()


    @Provides
    @Singleton
    fun provideLocalDateTimeProvider(): LocalDateTimeProvider =
        DefaultLocalDateTimeProvider()

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
    fun provideGetCategoriesUseCase(categoryCoreServiceAPI: CategoryCoreServiceAPI): GetCategoriesUseCase =
        GetCategoriesUseCase(
            categoryCoreService = categoryCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideAddExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): AddExpenseUseCase =
        DefaultAddExpenseUseCase(
            expenseCoreService = expenseCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideGetExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): GetExpenseUseCase =
        DefaultGetExpenseUseCase(
            expenseCoreService = expenseCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideUpdateExpenseUseCase(expenseCoreServiceAPI: ExpenseCoreServiceAPI): UpdateExpenseUseCase =
        DefaultUpdateExpenseUseCase(
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
        DefaultGetCategoriesQuickSummaryUseCase(
            categoriesQuickSummary = categoriesQuickSummaryAPI,
        )

    @Provides
    @Singleton
    fun provideSearchServiceUseCase(searchServiceAPI: SearchServiceAPI): SearchServiceUseCase =
        SearchServiceUseCase(
            searchService = searchServiceAPI,
        )

    @Provides
    @Singleton
    fun provideExportV1UseCase(
        expenseCoreServiceAPI: ExpenseCoreServiceAPI,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
        transactionManager: TransactionManager,
    ): ExportV1UseCase =
        ExportV1UseCase(
            categoryCoreServiceAPI = categoryCoreServiceAPI,
            expenseCoreServiceAPI = expenseCoreServiceAPI,
            transactionManager = transactionManager,
        )

    @Provides
    @Singleton
    fun provideAllExpensesRemover(
        searchServiceAPI: SearchServiceAPI,
        categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
        expenseCoreServiceAPI: ExpenseCoreServiceAPI,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
    ): AllExpensesRemover =
        AllExpensesRemover(
            searchServiceAPI = searchServiceAPI,
            categoriesQuickSummaryAPI = categoriesQuickSummaryAPI,
            expenseCoreServiceAPI = expenseCoreServiceAPI,
            categoryCoreServiceAPI = categoryCoreServiceAPI,
        )

    @Provides
    @Singleton
    fun provideImportV1UseCase(
        expenseCoreServiceAPI: ExpenseCoreServiceAPI,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
        allExpensesRemover: AllExpensesRemover,
        transactionManager: TransactionManager,
    ): ImportV1UseCase =
        ImportV1UseCase(
            categoryCoreServiceAPI = categoryCoreServiceAPI,
            expenseCoreServiceAPI = expenseCoreServiceAPI,
            allExpensesRemover = allExpensesRemover,
            transactionManager = transactionManager,
        )

    @Provides
    @Singleton
    fun provideExpenseAppUseCases(
        createCategoryUseCase: CreateCategoryUseCase,
        updateCategoryUseCase: UpdateCategoryUseCase,
        removeCategoryUseCase: RemoveCategoryUseCase,
        getCategoriesUseCase: GetCategoriesUseCase,
        // Expense
        addExpenseUseCase: AddExpenseUseCase,
        getExpenseUseCase: GetExpenseUseCase,
        updateExpenseUseCase: UpdateExpenseUseCase,
        removeExpenseUseCase: RemoveExpenseUseCase,

        getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
        searchServiceUseCase: SearchServiceUseCase,

        // import & export
        exportV1UseCase: ExportV1UseCase,
        importV1UseCase: ImportV1UseCase,
    ): ExpenseAppUseCases =
        ExpenseAppUseCases(
            createCategoryUseCase = createCategoryUseCase,
            updateCategoryUseCase = updateCategoryUseCase,
            removeCategoryUseCase = removeCategoryUseCase,
            getCategoriesUseCase = getCategoriesUseCase,
            // Expense
            addExpenseUseCase = addExpenseUseCase,
            getExpenseUseCase = getExpenseUseCase,
            updateExpenseUseCase = updateExpenseUseCase,
            removeExpenseUseCase = removeExpenseUseCase,

            getCategoriesQuickSummaryUseCase = getCategoriesQuickSummaryUseCase,
            searchServiceUseCase = searchServiceUseCase,

            exportV1UseCase = exportV1UseCase,
            importV1UseCase = importV1UseCase,
        )

}
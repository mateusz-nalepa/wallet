package com.mateuszcholyn.wallet.backend.impl.di.services

import com.mateuszcholyn.wallet.backend.api.AllBackendServices
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryFacade
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryFacade
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.minikafka.MiniKafka
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.minikafka.core.category.MiniKafkaCategoryPublisher
import com.mateuszcholyn.wallet.backend.impl.infrastructure.minikafka.core.expense.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltServicesModuleV2 {

    @Provides
    @Singleton
    fun provideMiniKafka(): MiniKafka =
        MiniKafka()


    @Provides
    @Singleton
    fun provideCategoriesQuickSummaryAPI(
        categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
        miniKafka: MiniKafka,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
    ): CategoriesQuickSummaryAPI {
        val categoriesQuickSummary = CategoriesQuickSummaryIMPL(
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            categoryCoreServiceAPI = categoryCoreServiceAPI,
        )

        miniKafka.expenseAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseAdded(it)
        }
        miniKafka.expenseUpdatedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseUpdated(it)
        }
        miniKafka.expenseRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleEventExpenseRemoved(it)
        }
        miniKafka.categoryAddedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryAdded(it)
        }
        miniKafka.categoryRemovedEventTopic.addSubscription {
            categoriesQuickSummary.handleCategoryRemoved(it)
        }

        return categoriesQuickSummary
    }

    @Provides
    @Singleton
    fun provideSearchServiceAPI(
        searchServiceRepository: SearchServiceRepository,
        miniKafka: MiniKafka,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
    ): SearchServiceAPI {
        val searchService = SearchServiceIMPL(
            searchServiceRepository = searchServiceRepository,
            categoryCoreServiceAPI = categoryCoreServiceAPI,
        )

        miniKafka.expenseAddedEventTopic.addSubscription {
            searchService.handleEventExpenseAdded(it)
        }
        miniKafka.expenseUpdatedEventTopic.addSubscription {
            searchService.handleEventExpenseUpdated(it)
        }
        miniKafka.expenseRemovedEventTopic.addSubscription {
            searchService.handleEventExpenseRemoved(it)
        }

        return searchService
    }

    @Provides
    @Singleton
    fun provideExpenseCoreServiceAPI(
        expenseRepositoryV2: ExpenseRepositoryV2,
        miniKafka: MiniKafka,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
        transactionManager: TransactionManager,
    ): ExpenseCoreServiceAPI =
        ExpenseCoreServiceIMPL(
            expenseRepositoryFacade = ExpenseRepositoryFacade(expenseRepositoryV2),
            expensePublisher = MiniKafkaExpensePublisher(miniKafka),
            categoryCoreServiceAPI = categoryCoreServiceAPI,
            transactionManager = transactionManager,
        )

    @Provides
    @Singleton
    fun provideCategoryCoreServiceAPI(
        categoryRepositoryV2: CategoryRepositoryV2,
        miniKafka: MiniKafka,
        transactionManager: TransactionManager,
    ): CategoryCoreServiceAPI =
        CategoryCoreServiceIMPL(
            categoryRepositoryFacade = CategoryRepositoryFacade(categoryRepositoryV2),
            categoryPublisher = MiniKafkaCategoryPublisher(miniKafka),
            transactionManager = transactionManager,
        )

    @Provides
    @Singleton
    fun provideAllBackendServices(
        categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
        searchServiceAPI: SearchServiceAPI,
        expenseCoreServiceAPI: ExpenseCoreServiceAPI,
        categoryCoreServiceAPI: CategoryCoreServiceAPI,
    ): AllBackendServices =
        AllBackendServices(
            categoriesQuickSummaryAPI = categoriesQuickSummaryAPI,
            searchServiceAPI = searchServiceAPI,
            expenseCoreServiceAPI = expenseCoreServiceAPI,
            categoryCoreServiceAPI = categoryCoreServiceAPI,
        )

}

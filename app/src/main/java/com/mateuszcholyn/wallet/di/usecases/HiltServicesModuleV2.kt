package com.mateuszcholyn.wallet.di.usecases

import com.mateuszcholyn.wallet.newcode.app.backend.AllBackendServices
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.*
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.*
import com.mateuszcholyn.wallet.newcode.app.backend.events.MiniKafka
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
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
    fun provideMiniKafka(): MiniKafka {
        return MiniKafka()
    }


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
        categoryCoreServiceAPI: CategoryCoreServiceAPI
    ): ExpenseCoreServiceAPI {
        return ExpenseCoreServiceIMPL(
            expenseRepositoryFacade = ExpenseRepositoryFacade(expenseRepositoryV2),
            expensePublisher = MiniKafkaExpensePublisher(miniKafka),
            categoryCoreServiceAPI = categoryCoreServiceAPI,
        )
    }

    @Provides
    @Singleton
    fun provideCategoryCoreServiceAPI(
        categoryRepositoryV2: CategoryRepositoryV2,
        miniKafka: MiniKafka,
    ): CategoryCoreServiceAPI {
        return CategoryCoreServiceIMPL(
            categoryRepositoryFacade = CategoryRepositoryFacade(categoryRepositoryV2),
            categoryPublisher = MiniKafkaCategoryPublisher(miniKafka),
        )
    }

    @Provides
    @Singleton
    fun provideBackendIsConfigured(
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

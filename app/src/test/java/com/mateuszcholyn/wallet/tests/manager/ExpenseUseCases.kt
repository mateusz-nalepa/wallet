package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.core.*
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.backend.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.backend.usecase.*


class ExpenseAppDependencies {

    private var inMemoryCoreRepository = InMemoryCoreRepository()
    var categoryRepository: CategoryRepository = inMemoryCoreRepository
    var expenseRepository: ExpenseRepository = inMemoryCoreRepository

    var categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository =
        InMemoryCategoriesQuickSummaryRepository()

    var searchServiceRepository: SearchServiceRepository = InMemorySearchServiceRepository()
}

data class ExpenseAppUseCases(
    val addExpenseUseCase: AddExpenseUseCase,
    val createCategoryUseCase: CreateCategoryUseCase,
    val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    val searchServiceUseCase: SearchServiceUseCase,
    val removeExpenseUseCase: RemoveExpenseUseCase,
    val removeCategoryUseCase: RemoveCategoryUseCase,
) {

    companion object {
        fun createFrom(deps: ExpenseAppDependencies): ExpenseAppUseCases {

            val categoriesQuickSummary =
                CategoriesQuickSummaryIMPL(
                    categoriesQuickSummaryRepository = deps.categoriesQuickSummaryRepository
                )

            val getCategoriesQuickSummaryUseCase =
                GetCategoriesQuickSummaryUseCase(
                    categoriesQuickSummaryAPI = categoriesQuickSummary,
                )


            val searchService =
                SearchServiceIMPL(
                    searchServiceRepository = deps.searchServiceRepository
                )

            val miniKafka = MiniKafka()
            miniKafka.expenseAddedEventTopic.addSubscription {
                categoriesQuickSummary.handleEventExpenseAdded(it)
            }
            miniKafka.expenseAddedEventTopic.addSubscription {
                searchService.handleEventExpenseAdded(it)
            }

            miniKafka.categoryAddedEventTopic.addSubscription {
                categoriesQuickSummary.handleCategoryAdded(it)
            }
            miniKafka.categoryRemovedEventTopic.addSubscription {
                categoriesQuickSummary.handleCategoryRemoved(it)
            }
            miniKafka.expenseRemovedEventTopic.addSubscription {
                categoriesQuickSummary.handleEventExpenseRemoved(it)
            }
            miniKafka.expenseRemovedEventTopic.addSubscription {
                searchService.handleEventExpenseRemoved(it)
            }

            val categoryPublisher =
                MiniKafkaCategoryPublisher(miniKafka)

            val categoryCoreService =
                CategoryCoreServiceIMPL(
                    categoryRepository = deps.categoryRepository,
                    categoryPublisher = categoryPublisher,
                )

            val createCategoryUseCase =
                CreateCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                )

            val expensePublisher =
                MiniKafkaExpensePublisher(miniKafka)

            val expenseCoreService =
                ExpenseCoreServiceIMPL(
                    expenseRepository = deps.expenseRepository,
                    expensePublisher = expensePublisher,
                )

            val addExpenseUseCase =
                AddExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                )

            val searchServiceUseCase =
                SearchServiceUseCase(
                    searchServiceAPI = searchService,
                )

            val removeExpenseUseCase =
                RemoveExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                )

            val removeCategoryUseCase =
                RemoveCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                )

            return ExpenseAppUseCases(
                createCategoryUseCase = createCategoryUseCase,
                addExpenseUseCase = addExpenseUseCase,
                getCategoriesQuickSummaryUseCase = getCategoriesQuickSummaryUseCase,
                searchServiceUseCase = searchServiceUseCase,
                removeExpenseUseCase = removeExpenseUseCase,
                removeCategoryUseCase = removeCategoryUseCase,
            )
        }

    }

}

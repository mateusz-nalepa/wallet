package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.categorycore.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.categorycore.CategoryRepository
import com.mateuszcholyn.wallet.backend.categorycore.InMemoryCategoryRepository
import com.mateuszcholyn.wallet.backend.categorycore.MiniKafkaCategoryPublisher
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseRepository
import com.mateuszcholyn.wallet.backend.expensecore.InMemoryExpenseRepository
import com.mateuszcholyn.wallet.backend.expensecore.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.backend.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.usecase.*


class ExpenseAppDependencies {
    var categoryRepository: CategoryRepository = InMemoryCategoryRepository()
    var expenseRepository: ExpenseRepository = InMemoryExpenseRepository()

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
            miniKafka.expenseRemovedEventTopic.addSubscription {
                categoriesQuickSummary.handleEventExpenseRemoved(it)
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

            return ExpenseAppUseCases(
                createCategoryUseCase = createCategoryUseCase,
                addExpenseUseCase = addExpenseUseCase,
                getCategoriesQuickSummaryUseCase = getCategoriesQuickSummaryUseCase,
                searchServiceUseCase = searchServiceUseCase,
                removeExpenseUseCase = removeExpenseUseCase,
            )
        }

    }

}

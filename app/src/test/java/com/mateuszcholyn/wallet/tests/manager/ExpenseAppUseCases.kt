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

            val searchService =
                SearchServiceIMPL(
                    searchServiceRepository = deps.searchServiceRepository
                )

            val miniKafka = MiniKafka()
            MiniKafkaConfigurator.configure(
                MiniKafkaConfigParameters(
                    miniKafka = miniKafka,
                    searchService = searchService,
                    categoriesQuickSummary = categoriesQuickSummary,
                )
            )

            val categoryCoreService =
                CategoryCoreServiceIMPL(
                    categoryRepository = deps.categoryRepository,
                    categoryPublisher = MiniKafkaCategoryPublisher(miniKafka),
                )

            val expenseCoreService =
                ExpenseCoreServiceIMPL(
                    expenseRepository = deps.expenseRepository,
                    expensePublisher = MiniKafkaExpensePublisher(miniKafka),
                )

            return ExpenseAppUseCases(
                createCategoryUseCase = CreateCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                ),
                removeCategoryUseCase = RemoveCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                ),
                addExpenseUseCase = AddExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                ),
                removeExpenseUseCase = RemoveExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                ),
                getCategoriesQuickSummaryUseCase = GetCategoriesQuickSummaryUseCase(
                    categoriesQuickSummaryAPI = categoriesQuickSummary,
                ),
                searchServiceUseCase = SearchServiceUseCase(
                    searchServiceAPI = searchService,
                ),
            )
        }

    }

}

package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.app.backend.core.InMemoryCoreRepository
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryRepository
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryRepositoryFacade
import com.mateuszcholyn.wallet.app.backend.core.category.MiniKafkaCategoryPublisher
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseRepository
import com.mateuszcholyn.wallet.app.backend.core.expense.ExpenseRepositoryFacade
import com.mateuszcholyn.wallet.app.backend.core.expense.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.app.backend.events.MiniKafka
import com.mateuszcholyn.wallet.app.backend.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.app.backend.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.app.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.app.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.app.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.app.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.app.usecase.searchservice.SearchServiceUseCase


class ExpenseAppDependencies {

    private var inMemoryCoreRepository = InMemoryCoreRepository()
    var categoryRepository: CategoryRepository = inMemoryCoreRepository
    var expenseRepository: ExpenseRepository = inMemoryCoreRepository

    var categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository =
        InMemoryCategoriesQuickSummaryRepository()

    var searchServiceRepository: SearchServiceRepository = InMemorySearchServiceRepository()
}

data class ExpenseAppUseCases(
    val createCategoryUseCase: CreateCategoryUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val removeCategoryUseCase: RemoveCategoryUseCase,
    val addExpenseUseCase: AddExpenseUseCase,
    val updateExpenseUseCase: UpdateExpenseUseCase,
    val removeExpenseUseCase: RemoveExpenseUseCase,
    val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    val searchServiceUseCase: SearchServiceUseCase,
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
            MiniKafkaConfigurator(
                MiniKafkaConfigParameters(
                    miniKafka = miniKafka,
                    searchService = searchService,
                    categoriesQuickSummary = categoriesQuickSummary,
                )
            ).configure()

            val categoryCoreService =
                CategoryCoreServiceIMPL(
                    categoryRepositoryFacade = CategoryRepositoryFacade(deps.categoryRepository),
                    categoryPublisher = MiniKafkaCategoryPublisher(miniKafka),
                )

            val expenseCoreService =
                ExpenseCoreServiceIMPL(
                    expenseRepositoryFacade = ExpenseRepositoryFacade(deps.expenseRepository),
                    expensePublisher = MiniKafkaExpensePublisher(miniKafka),
                )

            return ExpenseAppUseCases(
                createCategoryUseCase = CreateCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                ),
                updateCategoryUseCase = UpdateCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                ),
                removeCategoryUseCase = RemoveCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                ),
                addExpenseUseCase = AddExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                ),
                updateExpenseUseCase = UpdateExpenseUseCase(
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

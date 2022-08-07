package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryFacade
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.MiniKafkaCategoryPublisher
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryFacade
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.newcode.app.backend.events.MiniKafka
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.searchservice.SearchServiceUseCase


class ExpenseAppDependencies {

    private var inMemoryCoreRepository = InMemoryCoreRepositoryV2()
    var categoryRepositoryV2: CategoryRepositoryV2 = inMemoryCoreRepository
    var expenseRepositoryV2: ExpenseRepositoryV2 = inMemoryCoreRepository

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
                    categoryRepositoryFacade = CategoryRepositoryFacade(deps.categoryRepositoryV2),
                    categoryPublisher = MiniKafkaCategoryPublisher(miniKafka),
                )

            val expenseCoreService =
                ExpenseCoreServiceIMPL(
                    expenseRepositoryFacade = ExpenseRepositoryFacade(deps.expenseRepositoryV2),
                    expensePublisher = MiniKafkaExpensePublisher(miniKafka),
                )

            return ExpenseAppUseCases(
                createCategoryUseCase = CreateCategoryUseCase(
                    categoryCoreService = categoryCoreService,
                ),
                updateCategoryUseCase = UpdateCategoryUseCase(
                    categoryCoreService = categoryCoreService,
                ),
                removeCategoryUseCase = RemoveCategoryUseCase(
                    categoryCoreService = categoryCoreService,
                ),
                addExpenseUseCase = AddExpenseUseCase(
                    expenseCoreService = expenseCoreService,
                ),
                updateExpenseUseCase = UpdateExpenseUseCase(
                    expenseCoreService = expenseCoreService,
                ),
                removeExpenseUseCase = RemoveExpenseUseCase(
                    expenseCoreService = expenseCoreService,
                ),
                getCategoriesQuickSummaryUseCase = GetCategoriesQuickSummaryUseCase(
                    categoriesQuickSummary = categoriesQuickSummary,
                ),
                searchServiceUseCase = SearchServiceUseCase(
                    searchService = searchService,
                ),
            )
        }

    }

}

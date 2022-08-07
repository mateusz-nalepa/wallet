package com.mateuszcholyn.wallet.tests.manager.setupunittest

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryFacade
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.MiniKafkaCategoryPublisher
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryFacade
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.newcode.app.backend.events.MiniKafka
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppDependencies
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppUseCases

internal fun createFrom(deps: ExpenseAppDependencies): ExpenseAppUseCases {

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

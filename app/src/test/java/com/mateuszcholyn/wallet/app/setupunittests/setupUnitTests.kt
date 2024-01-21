package com.mateuszcholyn.wallet.app.setupunittests

import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryFacade
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryFacade
import com.mateuszcholyn.wallet.backend.impl.domain.minikafka.MiniKafka
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.infrastructure.minikafka.core.category.MiniKafkaCategoryPublisher
import com.mateuszcholyn.wallet.backend.impl.infrastructure.minikafka.core.expense.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.manager.ExpenseAppManagerScope
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.manager.*

fun initExpenseAppManager(scope: ExpenseAppManagerScope.() -> Unit): ExpenseAppManager {

    val expenseAppManagerScope = ExpenseAppManagerScope().apply(scope)
    val expenseAppDependencies = ExpenseAppDependencies()
    val useCases = createFrom(expenseAppDependencies)

    ExpenseAppInitializer(
        expenseAppManagerScope = expenseAppManagerScope,
        expenseAppUseCases = useCases,
    ).init()

    return ExpenseAppManager(
        expenseAppDependencies = expenseAppDependencies,
        expenseAppUseCases = useCases
    )
}

internal fun createFrom(
    deps: ExpenseAppDependencies,
): ExpenseAppUseCases {
    val miniKafka = MiniKafka()

    val categoryCoreService =
        CategoryCoreServiceIMPL(
            categoryRepositoryFacade = CategoryRepositoryFacade(deps.categoryRepositoryV2),
            categoryPublisher = MiniKafkaCategoryPublisher(miniKafka),
        )

    val expenseCoreService =
        ExpenseCoreServiceIMPL(
            expenseRepositoryFacade = ExpenseRepositoryFacade(deps.expenseRepositoryV2),
            expensePublisher = MiniKafkaExpensePublisher(miniKafka),
            categoryCoreServiceAPI = categoryCoreService,
        )

    val categoriesQuickSummary =
        CategoriesQuickSummaryIMPL(
            categoriesQuickSummaryRepository = deps.categoriesQuickSummaryRepository,
            categoryCoreServiceAPI = categoryCoreService,
        )

    val searchService =
        SearchServiceIMPL(
            searchServiceRepository = deps.searchServiceRepository,
            categoryCoreServiceAPI = categoryCoreService,
        )

    MiniKafkaConfigurator(
        MiniKafkaConfigParameters(
            miniKafka = miniKafka,
            searchService = searchService,
            categoriesQuickSummary = categoriesQuickSummary,
        )
    ).configure()

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
        getExpenseUseCase = GetExpenseUseCase(
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
package com.mateuszcholyn.wallet.app.setupunittests

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchServiceAPI
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryFacade
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryFacade
import com.mateuszcholyn.wallet.backend.impl.domain.messagebus.MessageBus
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceIMPL
import com.mateuszcholyn.wallet.backend.impl.infrastructure.messagebus.core.category.MessageBusCategoryPublisher
import com.mateuszcholyn.wallet.backend.impl.infrastructure.messagebus.core.expense.MessageBusExpensePublisher
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.export.ExportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.AllExpensesRemover
import com.mateuszcholyn.wallet.frontend.domain.usecase.backup.impo.ImportV1UseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.manager.ExpenseAppDependencies
import com.mateuszcholyn.wallet.manager.ExpenseAppInitializer
import com.mateuszcholyn.wallet.manager.ExpenseAppManager
import com.mateuszcholyn.wallet.manager.ExpenseAppManagerScope

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
    val messageBus = MessageBus()

    val categoryCoreService: CategoryCoreServiceAPI =
        CategoryCoreServiceIMPL(
            categoryRepositoryFacade = CategoryRepositoryFacade(deps.categoryRepository),
            categoryPublisher = MessageBusCategoryPublisher(messageBus),
            transactionManager = deps.transactionManager,
        )

    val expenseCoreService: ExpenseCoreServiceAPI =
        ExpenseCoreServiceIMPL(
            expenseRepositoryFacade = ExpenseRepositoryFacade(deps.expenseRepository),
            expensePublisher = MessageBusExpensePublisher(messageBus),
            categoryCoreServiceAPI = categoryCoreService,
            transactionManager = deps.transactionManager,
        )

    val categoriesQuickSummary: CategoriesQuickSummaryAPI =
        CategoriesQuickSummaryIMPL(
            categoriesQuickSummaryRepository = deps.categoriesQuickSummaryRepository,
            categoryCoreServiceAPI = categoryCoreService,
        )

    val searchService: SearchServiceAPI =
        SearchServiceIMPL(
            searchServiceRepository = deps.searchServiceRepository,
            categoryCoreServiceAPI = categoryCoreService,
        )

    MessageBusConfigurator(
        MessageBusConfigParameters(
            messageBus = messageBus,
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
        exportV1UseCase = ExportV1UseCase(
            categoryCoreServiceAPI = categoryCoreService,
            expenseCoreServiceAPI = expenseCoreService,
            transactionManager = deps.transactionManager,
        ),
        importV1UseCase = ImportV1UseCase(
            categoryCoreServiceAPI = categoryCoreService,
            expenseCoreServiceAPI = expenseCoreService,
            allExpensesRemover = AllExpensesRemover(
                categoryCoreServiceAPI = categoryCoreService,
                expenseCoreServiceAPI = expenseCoreService,
                categoriesQuickSummaryAPI = categoriesQuickSummary,
                searchServiceAPI = searchService,
            ),
            transactionManager = deps.transactionManager,
        ),
    )
}

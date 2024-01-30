package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.EmptyTransactionManager
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import kotlinx.coroutines.runBlocking


class ExpenseAppManager(
    val expenseAppDependencies: ExpenseAppDependencies,
    val expenseAppUseCases: ExpenseAppUseCases,
)

data class ExpenseAppDependencies(

    private var inMemoryCoreRepository: InMemoryCoreRepositoryV2 = InMemoryCoreRepositoryV2(),
    val categoryRepositoryV2: CategoryRepositoryV2 = inMemoryCoreRepository,
    val expenseRepositoryV2: ExpenseRepositoryV2 = inMemoryCoreRepository,
    val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository = InMemoryCategoriesQuickSummaryRepository(),
    val searchServiceRepository: SearchServiceRepository = InMemorySearchServiceRepository(),
    val transactionManager: TransactionManager = EmptyTransactionManager(),
)


fun ExpenseAppManager.getAllCoreCategories(): List<CategoryV2> =
    runBlocking {
        expenseAppDependencies
            .categoryRepositoryV2
            .getAllCategories()
    }

fun ExpenseAppManager.getAllCoreExpenses(): List<ExpenseV2> =
    runBlocking {
        expenseAppDependencies
            .expenseRepositoryV2
            .getAllExpenses()
    }
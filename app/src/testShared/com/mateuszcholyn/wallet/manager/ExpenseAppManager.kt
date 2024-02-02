package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.InMemoryCoreRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepository
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

    private var inMemoryCoreRepository: InMemoryCoreRepository = InMemoryCoreRepository(),
    val categoryRepository: CategoryRepository = inMemoryCoreRepository,
    val expenseRepository: ExpenseRepository = inMemoryCoreRepository,
    val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository = InMemoryCategoriesQuickSummaryRepository(),
    val searchServiceRepository: SearchServiceRepository = InMemorySearchServiceRepository(),
    val transactionManager: TransactionManager = EmptyTransactionManager(),
)


fun ExpenseAppManager.getAllCoreCategories(): List<Category> =
    runBlocking {
        expenseAppDependencies
            .categoryRepository
            .getAllCategories()
    }

fun ExpenseAppManager.getAllCoreExpenses(): List<Expense> =
    runBlocking {
        expenseAppDependencies
            .expenseRepository
            .getAllExpenses()
    }
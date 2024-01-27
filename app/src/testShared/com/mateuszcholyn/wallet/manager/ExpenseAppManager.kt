package com.mateuszcholyn.wallet.manager

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.backend.impl.domain.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.frontend.domain.usecase.ExpenseAppUseCases


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
)


fun ExpenseAppManager.getAllCoreCategories(): List<CategoryV2> =
    this
        .expenseAppDependencies
        .categoryRepositoryV2
        .getAllCategories()

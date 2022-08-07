package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.InMemoryCoreRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.InMemorySearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.searchservice.SearchServiceUseCase


data class ExpenseAppDependencies(

    private var inMemoryCoreRepository: InMemoryCoreRepositoryV2 = InMemoryCoreRepositoryV2(),
    val categoryRepositoryV2: CategoryRepositoryV2 = inMemoryCoreRepository,
    val expenseRepositoryV2: ExpenseRepositoryV2 = inMemoryCoreRepository,
    val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository = InMemoryCategoriesQuickSummaryRepository(),
    val searchServiceRepository: SearchServiceRepository = InMemorySearchServiceRepository(),
)


data class ExpenseAppUseCases(
    val createCategoryUseCase: CreateCategoryUseCase,
    val updateCategoryUseCase: UpdateCategoryUseCase,
    val removeCategoryUseCase: RemoveCategoryUseCase,
    val addExpenseUseCase: AddExpenseUseCase,
    val updateExpenseUseCase: UpdateExpenseUseCase,
    val removeExpenseUseCase: RemoveExpenseUseCase,
    val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    val searchServiceUseCase: SearchServiceUseCase,
)
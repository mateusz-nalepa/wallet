package com.mateuszcholyn.wallet.newTests.setup

import com.mateuszcholyn.wallet.newcode.app.backend.AllBackendServices
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.searchservice.SearchServiceRepository
import com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.CreateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.RemoveCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.category.UpdateCategoryUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.RemoveExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.core.expense.UpdateExpenseUseCase
import com.mateuszcholyn.wallet.newcode.app.usecase.searchservice.SearchServiceUseCase
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppDependencies
import com.mateuszcholyn.wallet.tests.manager.ExpenseAppUseCases
import javax.inject.Inject

class ExpenseAppIntegrationManager @Inject constructor(
    // Use cases
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val removeCategoryUseCase: RemoveCategoryUseCase,
    private val addExpenseUseCase: AddExpenseUseCase,
    private val getExpenseUseCase: GetExpenseUseCase,
    private val updateExpenseUseCase: UpdateExpenseUseCase,
    private val removeExpenseUseCase: RemoveExpenseUseCase,
    private val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
    private val searchServiceUseCase: SearchServiceUseCase,

    // Dependencies
    private val categoryRepositoryV2: CategoryRepositoryV2,
    private val expenseRepositoryV2: ExpenseRepositoryV2,
    private val categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository,
    private val searchServiceRepository: SearchServiceRepository,

    // All App works
    private val allBackendServices: AllBackendServices,
) {

    fun expenseAppUseCases(): ExpenseAppUseCases =
        ExpenseAppUseCases(
            createCategoryUseCase = createCategoryUseCase,
            updateCategoryUseCase = updateCategoryUseCase,
            removeCategoryUseCase = removeCategoryUseCase,
            addExpenseUseCase = addExpenseUseCase,
            getExpenseUseCase = getExpenseUseCase,
            updateExpenseUseCase = updateExpenseUseCase,
            removeExpenseUseCase = removeExpenseUseCase,
            getCategoriesQuickSummaryUseCase = getCategoriesQuickSummaryUseCase,
            searchServiceUseCase = searchServiceUseCase,
        )

    fun dependencies(): ExpenseAppDependencies =
        ExpenseAppDependencies(
            categoryRepositoryV2 = categoryRepositoryV2,
            expenseRepositoryV2 = expenseRepositoryV2,
            categoriesQuickSummaryRepository = categoriesQuickSummaryRepository,
            searchServiceRepository = searchServiceRepository,
        )

}

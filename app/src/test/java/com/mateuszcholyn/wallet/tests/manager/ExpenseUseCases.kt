package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categorycore.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.categorycore.CategoryRepository
import com.mateuszcholyn.wallet.backend.categorycore.InMemoryCategoryRepository
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseRepository
import com.mateuszcholyn.wallet.backend.expensecore.InMemoryExpenseRepository
import com.mateuszcholyn.wallet.usecase.AddExpenseUseCase
import com.mateuszcholyn.wallet.usecase.CreateCategoryUseCase


class ExpenseAppDependencies {
    var categoryRepository: CategoryRepository = InMemoryCategoryRepository()
    var expenseRepository: ExpenseRepository = InMemoryExpenseRepository()
}

data class ExpenseAppUseCases(
    val addExpenseUseCase: AddExpenseUseCase,
    val createCategoryUseCase: CreateCategoryUseCase,
) {

    companion object {
        fun createFrom(expenseAppDependencies: ExpenseAppDependencies): ExpenseAppUseCases {
            val categoryCoreService =
                CategoryCoreServiceIMPL(
                    categoryRepository = expenseAppDependencies.categoryRepository,
                )

            val createCategoryUseCase =
                CreateCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                )

            val expenseCoreService =
                ExpenseCoreServiceIMPL(
                    expenseRepository = expenseAppDependencies.expenseRepository,
                )

            val addExpenseUseCase =
                AddExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                )

            return ExpenseAppUseCases(
                createCategoryUseCase = createCategoryUseCase,
                addExpenseUseCase = addExpenseUseCase,
            )
        }

    }

}

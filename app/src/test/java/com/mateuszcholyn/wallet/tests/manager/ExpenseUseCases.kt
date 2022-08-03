package com.mateuszcholyn.wallet.tests.manager

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryIMPL
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.InMemoryCategoriesQuickSummaryRepository
import com.mateuszcholyn.wallet.backend.categorycore.CategoryCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.categorycore.CategoryRepository
import com.mateuszcholyn.wallet.backend.categorycore.InMemoryCategoryRepository
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.backend.events.Subscriber
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseCoreServiceIMPL
import com.mateuszcholyn.wallet.backend.expensecore.ExpenseRepository
import com.mateuszcholyn.wallet.backend.expensecore.InMemoryExpenseRepository
import com.mateuszcholyn.wallet.backend.expensecore.MiniKafkaExpensePublisher
import com.mateuszcholyn.wallet.usecase.AddExpenseUseCase
import com.mateuszcholyn.wallet.usecase.CreateCategoryUseCase
import com.mateuszcholyn.wallet.usecase.GetCategoriesQuickSummaryUseCase


class ExpenseAppDependencies {
    var categoryRepository: CategoryRepository = InMemoryCategoryRepository()
    var expenseRepository: ExpenseRepository = InMemoryExpenseRepository()
    var categoriesQuickSummaryRepository: CategoriesQuickSummaryRepository =
        InMemoryCategoriesQuickSummaryRepository()
}

data class ExpenseAppUseCases(
    val addExpenseUseCase: AddExpenseUseCase,
    val createCategoryUseCase: CreateCategoryUseCase,
    val getCategoriesQuickSummaryUseCase: GetCategoriesQuickSummaryUseCase,
) {

    companion object {
        fun createFrom(deps: ExpenseAppDependencies): ExpenseAppUseCases {

            val categoriesQuickSummary =
                CategoriesQuickSummaryIMPL(
                    categoriesQuickSummaryRepository = deps.categoriesQuickSummaryRepository
                )

            val getCategoriesQuickSummaryUseCase =
                GetCategoriesQuickSummaryUseCase(
                    categoriesQuickSummaryAPI = categoriesQuickSummary,
                )

            val categoriesQuickSubscriber =
                Subscriber<ExpenseAddedEvent> {
                    categoriesQuickSummary.handleEventExpenseAdded(it)
                }

            val miniKafka = MiniKafka()
            miniKafka.expenseAddedEventTopic.addSubscriber(categoriesQuickSubscriber)

            val categoryCoreService =
                CategoryCoreServiceIMPL(
                    categoryRepository = deps.categoryRepository,
                )

            val createCategoryUseCase =
                CreateCategoryUseCase(
                    categoryCoreServiceAPI = categoryCoreService,
                )

            val expensePublisher =
                MiniKafkaExpensePublisher(miniKafka)

            val expenseCoreService =
                ExpenseCoreServiceIMPL(
                    expenseRepository = deps.expenseRepository,
                    expensePublisher = expensePublisher,
                )

            val addExpenseUseCase =
                AddExpenseUseCase(
                    expenseCoreServiceAPI = expenseCoreService,
                )



            return ExpenseAppUseCases(
                createCategoryUseCase = createCategoryUseCase,
                addExpenseUseCase = addExpenseUseCase,
                getCategoriesQuickSummaryUseCase = getCategoriesQuickSummaryUseCase,
            )
        }

    }

}

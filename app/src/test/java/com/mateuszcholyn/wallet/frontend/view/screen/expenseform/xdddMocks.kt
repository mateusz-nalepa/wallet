package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
import com.mateuszcholyn.wallet.frontend.di.usecases.LocalDateTimeProvider
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.AddExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.GetExpenseUseCase
import com.mateuszcholyn.wallet.frontend.domain.usecase.core.expense.UpdateExpenseUseCase
import java.time.LocalDateTime

class TestGetCategoriesQuickSummaryUseCase : GetCategoriesQuickSummaryUseCase {

    private var willThrowException: Boolean = false
    private var categories: List<CategoryQuickSummary> = emptyList()


    fun willReturnCategories(givenCategories: List<CategoryQuickSummary>) {
        categories = givenCategories
    }

    override suspend fun invoke(): QuickSummaryList {
        if (willThrowException) {
            TODO("Not yet implemented")
        }

        return QuickSummaryList(
            quickSummaries = categories,
        )
    }
}

class TestAddExpenseUseCase : AddExpenseUseCase {
    override suspend fun invoke(addExpenseParameters: AddExpenseParameters): Expense {
        TODO("Not yet implemented")
    }
}

class TestUpdateExpenseUseCase : UpdateExpenseUseCase {
    override suspend fun invoke(updatedExpense: Expense): Expense {
        TODO("Not yet implemented")
    }

}

class TestGetExpenseUseCase : GetExpenseUseCase {
    override suspend fun invoke(expenseId: ExpenseId): ExpenseWithCategory {
        TODO("Not yet implemented")
    }
}

class TestLocalDateTimeProvider : LocalDateTimeProvider {

    var dateTimeToBeReturned = LocalDateTime.now()

    fun willReturnTime(localDateTime: LocalDateTime) {
        dateTimeToBeReturned = localDateTime
    }

    override fun now(): LocalDateTime =
        dateTimeToBeReturned

}
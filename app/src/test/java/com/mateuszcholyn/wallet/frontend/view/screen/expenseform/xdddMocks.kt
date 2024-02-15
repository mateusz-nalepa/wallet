package com.mateuszcholyn.wallet.frontend.view.screen.expenseform

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoryQuickSummary
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.frontend.di.usecases.LocalDateTimeProvider
import com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary.GetCategoriesQuickSummaryUseCase
import java.time.LocalDateTime

class TestGetCategoriesQuickSummaryUseCase : GetCategoriesQuickSummaryUseCase {

    private var willThrowException: Boolean = false
    private var categories: List<CategoryQuickSummary> = emptyList()


    fun willThrowException() {
        willThrowException = true
    }

    fun willReturnCategories(givenCategories: List<CategoryQuickSummary>) {
        categories = givenCategories
    }

    override suspend fun invoke(): QuickSummaryList {
        if (willThrowException) {
            throw RuntimeException()
        }

        return QuickSummaryList(
            quickSummaries = categories,
        )
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

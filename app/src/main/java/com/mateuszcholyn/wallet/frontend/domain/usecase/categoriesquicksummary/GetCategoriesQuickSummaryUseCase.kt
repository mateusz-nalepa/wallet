package com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase


interface GetCategoriesQuickSummaryUseCase : UseCase {
    suspend fun invoke(): QuickSummaryList
}

class DefaultGetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummary: CategoriesQuickSummaryAPI,
) : GetCategoriesQuickSummaryUseCase {

    override suspend fun invoke(): QuickSummaryList =
        categoriesQuickSummary.getQuickSummary()

}
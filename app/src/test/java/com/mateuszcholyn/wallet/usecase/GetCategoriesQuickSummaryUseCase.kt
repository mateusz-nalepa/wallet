package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.QuickSummaryList


class GetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
) : UseCase {

    fun invoke(): QuickSummaryList =
        categoriesQuickSummaryAPI.getQuickSummary()

}
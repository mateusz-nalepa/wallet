package com.mateuszcholyn.wallet.backend.usecase.quicksummary

import com.mateuszcholyn.wallet.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.usecase.UseCase


class GetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
) : UseCase {

    fun invoke(): QuickSummaryList =
        categoriesQuickSummaryAPI.getQuickSummary()

}
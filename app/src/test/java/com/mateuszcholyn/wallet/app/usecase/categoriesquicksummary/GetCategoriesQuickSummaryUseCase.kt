package com.mateuszcholyn.wallet.app.usecase.categoriesquicksummary

import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.app.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.app.usecase.UseCase


class GetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummaryAPI: CategoriesQuickSummaryAPI,
) : UseCase {

    fun invoke(): QuickSummaryList =
        categoriesQuickSummaryAPI.getQuickSummary()

}
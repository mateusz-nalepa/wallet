package com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase


class GetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummary: CategoriesQuickSummaryAPI,
) : UseCase {

    fun invoke(): QuickSummaryList =
        categoriesQuickSummary.getQuickSummary()

}
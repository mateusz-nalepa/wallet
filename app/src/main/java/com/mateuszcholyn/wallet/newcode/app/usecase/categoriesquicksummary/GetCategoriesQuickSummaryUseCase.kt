package com.mateuszcholyn.wallet.newcode.app.usecase.categoriesquicksummary

import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.newcode.app.backend.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase


class GetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummary: CategoriesQuickSummaryAPI,
) : UseCase {

    fun invoke(): QuickSummaryList =
        categoriesQuickSummary.getQuickSummary()

}
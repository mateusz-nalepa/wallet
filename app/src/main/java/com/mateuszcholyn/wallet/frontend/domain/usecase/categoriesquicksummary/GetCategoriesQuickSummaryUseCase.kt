package com.mateuszcholyn.wallet.frontend.domain.usecase.categoriesquicksummary

import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.CategoriesQuickSummaryAPI
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryList
import com.mateuszcholyn.wallet.backend.api.categoriesquicksummary.QuickSummaryListV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase


interface GetCategoriesQuickSummaryUseCase : UseCase {
    @Deprecated(message = "remove me after invokeV2 is used everywhere XD")
    suspend fun invoke(): QuickSummaryList
    suspend fun invokeV2(): QuickSummaryListV2
}

class DefaultGetCategoriesQuickSummaryUseCase(
    private val categoriesQuickSummary: CategoriesQuickSummaryAPI,
) : GetCategoriesQuickSummaryUseCase {

    override suspend fun invoke(): QuickSummaryList =
        categoriesQuickSummary.getQuickSummary()

    override suspend fun invokeV2(): QuickSummaryListV2 =
        categoriesQuickSummary.getQuickSummaryV2()

}
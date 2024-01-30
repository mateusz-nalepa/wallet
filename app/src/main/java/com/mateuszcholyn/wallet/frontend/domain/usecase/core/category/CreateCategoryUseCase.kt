package com.mateuszcholyn.wallet.frontend.domain.usecase.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

class CreateCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    suspend fun invoke(createCategoryParameters: CreateCategoryParameters): CategoryV2 =
        categoryCoreService.add(createCategoryParameters)

}
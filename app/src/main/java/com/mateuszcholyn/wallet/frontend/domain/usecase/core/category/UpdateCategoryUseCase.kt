package com.mateuszcholyn.wallet.frontend.domain.usecase.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

class UpdateCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    suspend fun invoke(updatedCategory: CategoryV2): CategoryV2 =
        categoryCoreService.update(updatedCategory)

}
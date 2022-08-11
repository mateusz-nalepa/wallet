package com.mateuszcholyn.wallet.newcode.app.usecase.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class UpdateCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(updatedCategory: CategoryV2): CategoryV2 =
        categoryCoreService.update(updatedCategory)

}
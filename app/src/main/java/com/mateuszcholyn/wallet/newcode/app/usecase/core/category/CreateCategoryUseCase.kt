package com.mateuszcholyn.wallet.newcode.app.usecase.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class CreateCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): CategoryV2 =
        categoryCoreService.add(createCategoryParameters)

}
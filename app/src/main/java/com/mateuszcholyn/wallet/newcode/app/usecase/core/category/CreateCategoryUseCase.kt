package com.mateuszcholyn.wallet.newcode.app.usecase.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.Category
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class CreateCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): Category =
        categoryCoreService.add(createCategoryParameters)

}
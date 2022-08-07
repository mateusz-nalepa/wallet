package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.core.category.Category
import com.mateuszcholyn.wallet.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.category.CreateCategoryParameters

class CreateCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): Category =
        categoryCoreServiceAPI.add(createCategoryParameters)

}
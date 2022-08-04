package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.core.Category
import com.mateuszcholyn.wallet.backend.core.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.CreateCategoryParameters

class CreateCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): Category =
        categoryCoreServiceAPI.add(createCategoryParameters)

}
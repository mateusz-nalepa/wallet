package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.categorycore.Category
import com.mateuszcholyn.wallet.backend.categorycore.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.categorycore.CreateCategoryParameters

class CreateCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): Category =
        categoryCoreServiceAPI.add(createCategoryParameters)

}
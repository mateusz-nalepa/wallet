package com.mateuszcholyn.wallet.backend.usecase.core.category

import com.mateuszcholyn.wallet.backend.core.category.Category
import com.mateuszcholyn.wallet.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.usecase.UseCase

class CreateCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): Category =
        categoryCoreServiceAPI.add(createCategoryParameters)

}
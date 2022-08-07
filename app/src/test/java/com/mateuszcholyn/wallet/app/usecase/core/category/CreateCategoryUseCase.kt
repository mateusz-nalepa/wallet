package com.mateuszcholyn.wallet.app.usecase.core.category

import com.mateuszcholyn.wallet.app.backend.core.category.Category
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.app.backend.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.app.usecase.UseCase

class CreateCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(createCategoryParameters: CreateCategoryParameters): Category =
        categoryCoreServiceAPI.add(createCategoryParameters)

}
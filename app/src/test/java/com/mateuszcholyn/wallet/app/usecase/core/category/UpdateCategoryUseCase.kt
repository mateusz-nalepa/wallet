package com.mateuszcholyn.wallet.app.usecase.core.category

import com.mateuszcholyn.wallet.app.backend.core.category.Category
import com.mateuszcholyn.wallet.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.app.usecase.UseCase

class UpdateCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(updateCategoryParameters: Category): Category =
        categoryCoreServiceAPI.update(updateCategoryParameters)

}
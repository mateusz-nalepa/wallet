package com.mateuszcholyn.wallet.newcode.app.usecase.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.Category
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

class UpdateCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(updateCategoryParameters: Category): Category =
        categoryCoreService.update(updateCategoryParameters)

}
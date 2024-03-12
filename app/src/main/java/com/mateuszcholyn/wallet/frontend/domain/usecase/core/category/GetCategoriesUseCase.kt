package com.mateuszcholyn.wallet.frontend.domain.usecase.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoriesForFrontend
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

class GetCategoriesUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    suspend fun invoke(): CategoriesForFrontend =
        categoryCoreService.getAllGrouped()

}
package com.mateuszcholyn.wallet.frontend.domain.usecase.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.frontend.domain.usecase.UseCase

enum class CategoryRemovedStatus {
    SUCCESS
}

class RemoveCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    suspend fun invoke(categoryId: CategoryId): CategoryRemovedStatus {
        categoryCoreService.remove(categoryId)
        return CategoryRemovedStatus.SUCCESS
    }

}
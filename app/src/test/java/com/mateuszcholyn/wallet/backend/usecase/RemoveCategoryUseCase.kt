package com.mateuszcholyn.wallet.backend.usecase

import com.mateuszcholyn.wallet.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.category.CategoryId

enum class CategoryRemovedStatus {
    SUCCESS
}

class RemoveCategoryUseCase(
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(categoryId: CategoryId): CategoryRemovedStatus {
        categoryCoreServiceAPI.remove(categoryId)
        return CategoryRemovedStatus.SUCCESS
    }

}
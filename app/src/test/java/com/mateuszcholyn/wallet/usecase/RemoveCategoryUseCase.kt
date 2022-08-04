package com.mateuszcholyn.wallet.usecase

import com.mateuszcholyn.wallet.backend.core.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.core.CategoryId

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
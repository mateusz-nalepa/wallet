package com.mateuszcholyn.wallet.newcode.app.usecase.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.usecase.UseCase

enum class CategoryRemovedStatus {
    SUCCESS
}

class RemoveCategoryUseCase(
    private val categoryCoreService: CategoryCoreServiceAPI,
) : UseCase {

    fun invoke(categoryId: CategoryId): CategoryRemovedStatus {
        categoryCoreService.remove(categoryId)
        return CategoryRemovedStatus.SUCCESS
    }

}
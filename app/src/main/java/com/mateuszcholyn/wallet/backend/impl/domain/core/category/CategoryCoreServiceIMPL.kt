package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID

class CategoryCoreServiceIMPL(
    private val categoryRepositoryFacade: CategoryRepositoryFacade,
    private val categoryPublisher: CategoryPublisher,
) : CategoryCoreServiceAPI {
    override fun add(createCategoryParameters: CreateCategoryParameters): CategoryV2 =
        createCategoryParameters
            .toNewCategory()
            .let { categoryRepositoryFacade.save(it) }
            .also { categoryPublisher.publishCategoryAddedEvent(it.toCategoryAddedEvent()) }

    override fun getAll(): List<CategoryV2> =
        categoryRepositoryFacade.getAllCategories()

    override fun getById(categoryId: CategoryId): CategoryV2? =
        categoryRepositoryFacade.getById(categoryId)

    override fun getByIdOrThrow(categoryId: CategoryId): CategoryV2 =
        categoryRepositoryFacade.getByIdOrThrow(categoryId)

    override fun remove(categoryId: CategoryId) {
        categoryRepositoryFacade
            .remove(categoryId)
            .also { categoryPublisher.publishCategoryRemovedEvent(CategoryRemovedEvent(categoryId)) }
    }

    override fun update(updateCategoryParameters: CategoryV2): CategoryV2 =
        categoryRepositoryFacade
            .getByIdOrThrow(updateCategoryParameters.id)
            .updateUsing(updateCategoryParameters)
            .let { categoryRepositoryFacade.save(it) }

    override fun removeAll() {
        categoryRepositoryFacade.removeAll()
    }

    private fun CreateCategoryParameters.toNewCategory(): CategoryV2 =
        CategoryV2(
            id = this.categoryId ?: CategoryId(randomUUID()),
            name = name,
        )

    private fun CategoryV2.toCategoryAddedEvent(): CategoryAddedEvent =
        CategoryAddedEvent(
            categoryId = id,
            name = name,
        )

    private fun CategoryV2.updateUsing(updateCategoryParameters: CategoryV2): CategoryV2 =
        this.copy(
            name = updateCategoryParameters.name,
        )
}


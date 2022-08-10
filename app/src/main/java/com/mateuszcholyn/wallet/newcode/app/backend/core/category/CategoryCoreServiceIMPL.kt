package com.mateuszcholyn.wallet.newcode.app.backend.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.util.randomUUID

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

    private fun CreateCategoryParameters.toNewCategory(): CategoryV2 =
        CategoryV2(
            id = CategoryId(randomUUID()),
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


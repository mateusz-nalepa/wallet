package com.mateuszcholyn.wallet.newcode.app.backend.core.category

import com.mateuszcholyn.wallet.newcode.app.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.util.randomUUID

class CategoryCoreServiceIMPL(
    private val categoryRepositoryFacade: CategoryRepositoryFacade,
    private val categoryPublisher: CategoryPublisher,
) : CategoryCoreServiceAPI {
    override fun add(createCategoryParameters: CreateCategoryParameters): Category =
        createCategoryParameters
            .toNewCategory()
            .let { categoryRepositoryFacade.save(it) }
            .also { categoryPublisher.publishCategoryAddedEvent(it.toCategoryAddedEvent()) }

    override fun getAll(): List<Category> =
        categoryRepositoryFacade.getAllCategories()

    override fun remove(categoryId: CategoryId) {
        categoryRepositoryFacade
            .remove(categoryId)
            .also { categoryPublisher.publishCategoryRemovedEvent(CategoryRemovedEvent(categoryId)) }
    }

    override fun update(updateCategoryParameters: Category): Category =
        categoryRepositoryFacade
            .getByIdOrThrow(updateCategoryParameters.id)
            .updateUsing(updateCategoryParameters)
            .let { categoryRepositoryFacade.save(it) }

    private fun CreateCategoryParameters.toNewCategory(): Category =
        Category(
            id = CategoryId(randomUUID()),
            name = name,
        )

    private fun Category.toCategoryAddedEvent(): CategoryAddedEvent =
        CategoryAddedEvent(
            categoryId = id,
            name = name,
        )

    private fun Category.updateUsing(updateCategoryParameters: Category): Category =
        this.copy(
            name = updateCategoryParameters.name,
        )
}


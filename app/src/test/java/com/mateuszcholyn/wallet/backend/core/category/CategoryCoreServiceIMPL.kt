package com.mateuszcholyn.wallet.backend.core.category

import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.randomUUID
import java.time.Instant

class CategoryCoreServiceIMPL(
    private val categoryRepositoryFacade: CategoryRepositoryFacade,
    private val categoryPublisher: CategoryPublisher,
) : CategoryCoreServiceAPI {
    override fun add(createCategoryParameters: CreateCategoryParameters): Category =
        createCategoryParameters
            .toNewCategory()
            .let { categoryRepositoryFacade.add(it) }
            .also { categoryPublisher.publishCategoryAddedEvent(it.toCategoryAddedEvent()) }

    override fun getAll(): List<Category> =
        categoryRepositoryFacade.getAllCategories()

    override fun remove(categoryId: CategoryId) {
        categoryRepositoryFacade
            .remove(categoryId)
            .also { categoryPublisher.publishCategoryRemovedEvent(CategoryRemovedEvent(categoryId)) }
    }

    private fun CreateCategoryParameters.toNewCategory(): Category =
        Category(
            id = CategoryId(randomUUID()),
            name = name,
            createdAt = Instant.now(),
        )

    private fun Category.toCategoryAddedEvent(): CategoryAddedEvent =
        CategoryAddedEvent(
            categoryId = id,
            name = name,
        )
}


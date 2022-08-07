package com.mateuszcholyn.wallet.backend.core.category

import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.CategoryRemovedEvent
import com.mateuszcholyn.wallet.randomUUID
import java.time.Instant

class CategoryCoreServiceIMPL(
    private val categoryRepository: CategoryRepository,
    private val categoryPublisher: CategoryPublisher,
) : CategoryCoreServiceAPI {
    override fun add(createCategoryParameters: CreateCategoryParameters): Category =
        createCategoryParameters
            .toNewCategory()
            .let { categoryRepository.add(it) }
            .also { categoryPublisher.publishCategoryAddedEvent(it.toCategoryAddedEvent()) }

    override fun getAll(): List<Category> =
        categoryRepository.getAllCategories()

    override fun remove(categoryId: CategoryId) {
        categoryRepository
            .remove(
                categoryId = categoryId,
                onExpensesExistAction = {
                    throw CategoryHasExpensesException(it)
                }
            )
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

class CategoryHasExpensesException(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} has expenses and cannot be removed")
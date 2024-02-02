package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID

class CategoryCoreServiceIMPL(
    private val categoryRepositoryFacade: CategoryRepositoryFacade,
    private val categoryPublisher: CategoryPublisher,
    private val transactionManager: TransactionManager,
) : CategoryCoreServiceAPI {
    override suspend fun add(createCategoryParameters: CreateCategoryParameters): Category =
        transactionManager.runInTransaction {
            createCategoryParameters
                .toNewCategory()
                .let { categoryRepositoryFacade.create(it) }
                .also { categoryPublisher.publishCategoryAddedEvent(it.toCategoryAddedEvent()) }
        }

    override suspend fun getAll(): List<Category> =
        categoryRepositoryFacade.getAllCategories()

    override suspend fun getById(categoryId: CategoryId): Category? =
        categoryRepositoryFacade.getById(categoryId)

    override suspend fun getByIdOrThrow(categoryId: CategoryId): Category =
        categoryRepositoryFacade.getByIdOrThrow(categoryId)

    override suspend fun remove(categoryId: CategoryId) {
        transactionManager.runInTransaction {
            categoryRepositoryFacade
                .remove(categoryId)
                .also { categoryPublisher.publishCategoryRemovedEvent(CategoryRemovedEvent(categoryId)) }

        }
    }

    override suspend fun update(updateCategoryParameters: Category): Category =
        transactionManager.runInTransaction {
            categoryRepositoryFacade
                .getByIdOrThrow(updateCategoryParameters.id)
                .updateUsing(updateCategoryParameters)
                .let { categoryRepositoryFacade.update(it) }
        }

    override suspend fun removeAll() {
        categoryRepositoryFacade.removeAll()
    }

    private fun CreateCategoryParameters.toNewCategory(): Category =
        Category(
            id = this.categoryId ?: CategoryId(randomUUID()),
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


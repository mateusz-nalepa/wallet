package com.mateuszcholyn.wallet.backend.impl.domain.core.category

import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CreateCategoryParameters
import com.mateuszcholyn.wallet.backend.api.core.category.MainCategory
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategory
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManager
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID

class CategoryCoreServiceIMPL(
    private val categoryRepositoryFacade: CategoryRepositoryFacade,
    private val categoryPublisher: CategoryPublisher,
    private val transactionManager: TransactionManager,
) : CategoryCoreServiceAPI {
    override suspend fun add(createCategoryParameters: CreateCategoryParameters): Category =
        transactionManager.runInTransaction {
            validateAddCategory(createCategoryParameters)
            createCategoryParameters
                .toNewCategory()
                .let { categoryRepositoryFacade.create(it) }
                .also { categoryPublisher.publishCategoryAddedEvent(it.toCategoryAddedEvent()) }
        }

    private suspend fun validateAddCategory(createCategoryParameters: CreateCategoryParameters) {
        if (createCategoryParameters.parentCategory == null) {
            return
        }
        val parentCategory =
            categoryRepositoryFacade.getById(createCategoryParameters.parentCategory.id)
        if (parentCategory?.parentCategory != null) {
            throw UnableToAddSubCategoryToExistingSubCategoryException(createCategoryParameters.parentCategory.id)
        }
    }

    override suspend fun getAll(): List<Category> =
        categoryRepositoryFacade.getAllCategories()

    // TODO: HODOR - teraz to koduj
    override suspend fun getAllGrouped(): List<MainCategory> =
        groupCategories(getAll())

    private fun groupCategories(
        categories: List<Category>,
    ): List<MainCategory> {
        val mainCategories = categories.filter { it.parentCategory == null }

        val mainCategoryWithSubCategories =
            mainCategories.map { mainCategory ->
                MainCategory(
                    id = mainCategory.id,
                    name = mainCategory.name,
                    subCategories = categories
                        .filter { it.parentCategory?.id == mainCategory.id }
                        .map { subCategory ->
                            SubCategory(
                                id = subCategory.id,
                                name = subCategory.name,
                            )
                        }
                )
            }

        return mainCategoryWithSubCategories
    }

    override suspend fun getById(categoryId: CategoryId): Category? =
        categoryRepositoryFacade.getById(categoryId)

    override suspend fun getByIdOrThrow(categoryId: CategoryId): Category =
        categoryRepositoryFacade.getByIdOrThrow(categoryId)

    override suspend fun remove(categoryId: CategoryId) {
        transactionManager.runInTransaction {
            categoryRepositoryFacade
                .remove(categoryId)
                .also {
                    categoryPublisher.publishCategoryRemovedEvent(
                        CategoryRemovedEvent(
                            categoryId
                        )
                    )
                }

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
            parentCategory = parentCategory,
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

class UnableToAddSubCategoryToExistingSubCategoryException(
    subCategoryId: CategoryId,
) : RuntimeException(
    "Unable to add subcategory to existing subcategory. Existing subCategoryId: ${subCategoryId.id}"
)


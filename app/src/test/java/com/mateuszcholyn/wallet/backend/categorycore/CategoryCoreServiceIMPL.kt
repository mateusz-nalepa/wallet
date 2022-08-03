package com.mateuszcholyn.wallet.backend.categorycore

import com.mateuszcholyn.wallet.backend.events.CategoryAddedEvent
import com.mateuszcholyn.wallet.backend.events.MiniKafka
import com.mateuszcholyn.wallet.randomUUID
import java.time.Instant

interface CategoryRepository {
    fun add(category: Category): Category
    fun getAll(): List<Category>
    fun getById(categoryId: CategoryId): Category?
}

class InMemoryCategoryRepository : CategoryRepository {
    private val storage: MutableMap<CategoryId, Category> = mutableMapOf()

    override fun add(category: Category): Category {
        storage[category.id] = category
        return category
    }

    override fun getAll(): List<Category> =
        storage.values.toList()

    override fun getById(categoryId: CategoryId): Category? =
        getAll()
            .find { it.id == categoryId }
}

interface CategoryPublisher {
    fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent)
}

class MiniKafkaCategoryPublisher(
    private val miniKafka: MiniKafka,
) : CategoryPublisher {
    override fun publishCategoryAddedEvent(categoryAddedEvent: CategoryAddedEvent) {
        miniKafka.categoryAddedEventTopic.publish(categoryAddedEvent)
    }
}


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
        categoryRepository.getAll()

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

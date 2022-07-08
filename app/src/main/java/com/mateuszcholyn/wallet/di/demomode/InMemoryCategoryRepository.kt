package com.mateuszcholyn.wallet.di.demomode

import com.mateuszcholyn.wallet.domain.category.Category
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryWithExpenses
import com.mateuszcholyn.wallet.domain.category.ExistingCategory
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository

class InMemoryCategoryRepository(
        private val expenseRepository: ExpenseRepository,
) : CategoryRepository {
    private val storage = mutableMapOf<Long, ExistingCategory>()
    private val idGenerator = IdGenerator()

    override fun remove(categoryId: Long): Boolean {
        storage.remove(categoryId)
        return true
    }

    override fun add(category: Category): ExistingCategory {
        val categoryId = idGenerator.nextNumber()

        val addedCategory =
                ExistingCategory(
                        id = categoryId,
                        name = category.name,
                )

        storage[categoryId] = addedCategory

        return addedCategory
    }

    override fun update(category: ExistingCategory): ExistingCategory {
        storage[category.id] = category

        return category
    }

    override fun getAllCategoriesWithExpenses(): List<CategoryWithExpenses> {
        return storage.values
                .map { category ->
                    CategoryWithExpenses(
                            category = category,
                            expenses = expenseRepository.getAll().filter { expense ->
                                expense.category.id == category.id
                            }
                    )
                }
    }
}

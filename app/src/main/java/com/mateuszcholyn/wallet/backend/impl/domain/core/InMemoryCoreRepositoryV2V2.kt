package com.mateuszcholyn.wallet.backend.impl.domain.core

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import java.util.concurrent.ConcurrentHashMap

class InMemoryCoreRepositoryV2 : CategoryRepositoryV2, ExpenseRepositoryV2 {

    private val categories: MutableMap<CategoryId, CategoryV2> = ConcurrentHashMap()
    private val expenses: MutableMap<ExpenseId, ExpenseV2> = ConcurrentHashMap()

    override suspend fun create(category: CategoryV2): CategoryV2 {
        categories[category.id] = category
        return category
    }

    override suspend fun update(category: CategoryV2): CategoryV2 {
        categories[category.id] = category
        return category
    }

    override suspend fun getAllCategories(): List<CategoryV2> {
        return categories.values.toList()
    }

    override suspend fun getById(categoryId: CategoryId): CategoryV2? {
        return categories[categoryId]
    }

    override suspend fun remove(
        categoryId: CategoryId,
        onExpensesExistAction: (CategoryId) -> Unit,
    ) {
        val numberOfExpensesInGivenCategory =
            getAllExpenses()
                .filter { it.categoryId == categoryId }
                .size

        if (numberOfExpensesInGivenCategory > 0) {
            onExpensesExistAction.invoke(categoryId)
        }
        categories.remove(categoryId)
    }

    override suspend fun removeAllCategories() {
        val ids = categories.values.toList().map { it.id }

        ids.forEach {
            categories.remove(it)
        }
    }

    override suspend fun removeAllExpenses() {
        val ids = expenses.values.toList().map { it.expenseId }

        ids.forEach {
            expenses.remove(it)
        }
    }

    override suspend fun create(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): ExpenseV2 {
        val categoryOrNull = categories[expense.categoryId]

        if (categoryOrNull == null) {
            onNonExistingCategoryAction.invoke(expense.categoryId)
        }

        expenses[expense.expenseId] = expense
        return expense
    }

    override suspend fun update(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): ExpenseV2 =
        create(expense, onNonExistingCategoryAction)

    override suspend fun getAllExpenses(): List<ExpenseV2> {
        return expenses.values.toList()
    }

    override suspend fun getById(expenseId: ExpenseId): ExpenseV2? {
        return expenses[expenseId]
    }

    override suspend fun remove(expenseId: ExpenseId) {
        expenses.remove(expenseId)
    }
}
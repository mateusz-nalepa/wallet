package com.mateuszcholyn.wallet.backend.impl.domain.core

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.Category
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.impl.domain.core.category.CategoryRepository
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepository
import java.util.concurrent.ConcurrentHashMap

class InMemoryCoreRepository : CategoryRepository, ExpenseRepository {

    private val categories: MutableMap<CategoryId, Category> = ConcurrentHashMap()
    private val expenses: MutableMap<ExpenseId, Expense> = ConcurrentHashMap()

    override suspend fun create(category: Category): Category {
        categories[category.id] = category
        return category
    }

    override suspend fun update(category: Category): Category {
        categories[category.id] = category
        return category
    }

    override suspend fun getAllCategories(): List<Category> {
        return categories.values.toList()
    }

    override suspend fun getById(categoryId: CategoryId): Category? {
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
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): Expense {
        val categoryOrNull = categories[expense.categoryId]

        if (categoryOrNull == null) {
            onNonExistingCategoryAction.invoke(expense.categoryId)
        }

        expenses[expense.expenseId] = expense
        return expense
    }

    override suspend fun update(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): Expense =
        create(expense, onNonExistingCategoryAction)

    override suspend fun getAllExpenses(): List<Expense> {
        return expenses.values.toList()
    }

    override suspend fun getById(expenseId: ExpenseId): Expense? {
        return expenses[expenseId]
    }

    override suspend fun remove(expenseId: ExpenseId) {
        expenses.remove(expenseId)
    }
}
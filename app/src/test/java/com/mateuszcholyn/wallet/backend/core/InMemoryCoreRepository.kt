package com.mateuszcholyn.wallet.backend.core

import java.util.concurrent.ConcurrentHashMap

class InMemoryCoreRepository : CategoryRepository, ExpenseRepository {

    private val categories: MutableMap<CategoryId, Category> = ConcurrentHashMap()
    private val expenses: MutableMap<ExpenseId, Expense> = ConcurrentHashMap()

    override fun add(category: Category): Category {
        categories[category.id] = category
        return category
    }

    override fun getAllCategories(): List<Category> {
        return categories.values.toList()
    }

    override fun getById(categoryId: CategoryId): Category? {
        return categories[categoryId]
    }

    override fun remove(
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

    override fun add(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): Expense {
        val categoryOrNull = categories[expense.categoryId]

        if (categoryOrNull == null) {
            onNonExistingCategoryAction.invoke(expense.categoryId)
        }

        expenses[expense.id] = expense
        return expense
    }

    override fun getAllExpenses(): List<Expense> {
        return expenses.values.toList()
    }

    override fun getById(expenseId: ExpenseId): Expense? {
        return expenses[expenseId]
    }

    override fun remove(expenseId: ExpenseId) {
        expenses.remove(expenseId)
    }
}
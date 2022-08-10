package com.mateuszcholyn.wallet.newcode.app.backend.core

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryRepositoryV2
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.Expense
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseId
import com.mateuszcholyn.wallet.newcode.app.backend.core.expense.ExpenseRepositoryV2
import java.util.concurrent.ConcurrentHashMap

class InMemoryCoreRepositoryV2 : CategoryRepositoryV2, ExpenseRepositoryV2 {

    private val categories: MutableMap<CategoryId, CategoryV2> = ConcurrentHashMap()
    private val expenses: MutableMap<ExpenseId, Expense> = ConcurrentHashMap()

    override fun save(category: CategoryV2): CategoryV2 {
        categories[category.id] = category
        return category
    }

    override fun getAllCategories(): List<CategoryV2> {
        return categories.values.toList()
    }

    override fun getById(categoryId: CategoryId): CategoryV2? {
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

    override fun save(
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
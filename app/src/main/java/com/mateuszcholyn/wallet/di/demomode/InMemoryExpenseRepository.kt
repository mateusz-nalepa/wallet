package com.mateuszcholyn.wallet.di.demomode

import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria

class InMemoryExpenseRepository : ExpenseRepository {
    private val storage = mutableMapOf<Long, Expense>()
    private val idGenerator = IdGenerator()

    override fun remove(expenseId: Long): Boolean {
        storage.remove(expenseId)
        return true
    }

    override fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return storage.values.toList()
    }

    override fun getAll(): List<Expense> {
        return storage.values.toList()
    }

    override fun add(expense: Expense): Expense {
        val expenseId = idGenerator.nextNumber()
        val addedExpense = expense.copy(id = expenseId)

        storage[expenseId] = addedExpense

        return addedExpense
    }

    override fun update(expense: Expense): Expense {
        storage[expense.id!!] = expense

        return expense
    }

    override fun getById(expenseId: Long): Expense {
        return storage[expenseId]!!
    }

}

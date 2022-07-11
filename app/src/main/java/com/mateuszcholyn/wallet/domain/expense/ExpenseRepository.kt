package com.mateuszcholyn.wallet.domain.expense

interface ExpenseRepository {

    fun remove(expenseId: Long): Boolean
    fun removeAll(): Boolean {
        return true
    }

    fun getAll(): List<Expense>
    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense>
    fun add(expense: Expense): Expense
    fun update(expense: Expense): Expense
    fun getById(expenseId: Long): Expense

}

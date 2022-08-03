package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent


interface SearchServiceAPI {
    fun handleEventExpenseAdded(expenseAddedEvent: ExpenseAddedEvent)
    fun getAll(): ExpensesList
}

data class ExpensesList(
    val expenses: List<ExpenseAddedEvent>,
)

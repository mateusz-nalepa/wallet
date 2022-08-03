package com.mateuszcholyn.wallet.backend.expensecore

import com.mateuszcholyn.wallet.randomUUID

interface ExpenseRepository {
    fun add(expense: Expense): Expense
    fun getAll(): List<Expense>
}

class InMemoryExpenseRepository : ExpenseRepository {
    private val storage: MutableMap<ExpenseId, Expense> = mutableMapOf()

    override fun add(expense: Expense): Expense {
        storage[expense.id] = expense
        return expense
    }

    override fun getAll(): List<Expense> =
        storage.values.toList()
}


class ExpenseCoreServiceIMPL(
    private val expenseRepository: ExpenseRepository,
) : ExpenseCoreServiceAPI {
    override fun add(createExpenseParameters: CreateExpenseParameters): Expense {
        TODO("Not yet implemented")
    }

    override fun getAll(): List<Expense> =
        expenseRepository.getAll()

    private fun CreateExpenseParameters.toNewExpense(): Expense =
        Expense(
            id = ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )
}

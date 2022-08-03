package com.mateuszcholyn.wallet.backend.expensecore

import com.mateuszcholyn.wallet.randomUUID

interface ExpenseRepository {
    fun add(expense: Expense): Expense
    fun getAll(): List<Expense>
    fun getById(expenseId: ExpenseId): Expense?
}

class InMemoryExpenseRepository : ExpenseRepository {
    private val storage: MutableMap<ExpenseId, Expense> = mutableMapOf()

    override fun add(expense: Expense): Expense {
        storage[expense.id] = expense
        return expense
    }

    override fun getAll(): List<Expense> =
        storage.values.toList()

    override fun getById(expenseId: ExpenseId): Expense? =
        getAll()
            .find { it.id == expenseId }
}


class ExpenseCoreServiceIMPL(
    private val expenseRepository: ExpenseRepository,
) : ExpenseCoreServiceAPI {
    override fun add(addExpenseParameters: AddExpenseParameters): Expense =
        addExpenseParameters
            .toNewExpense()
            .let { expenseRepository.add(it) }

    override fun getAll(): List<Expense> =
        expenseRepository.getAll()

    private fun AddExpenseParameters.toNewExpense(): Expense =
        Expense(
            id = ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )
}

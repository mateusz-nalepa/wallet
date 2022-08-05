package com.mateuszcholyn.wallet.backend.core

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.randomUUID


class ExpenseCoreServiceIMPL(
    private val expenseRepository: ExpenseRepository,
    private val expensePublisher: ExpensePublisher,
) : ExpenseCoreServiceAPI {
    override fun add(addExpenseParameters: AddExpenseParameters): Expense =
        addExpenseParameters
            .toNewExpense()
            .let { expense ->
                expenseRepository.add(
                    expense = expense,
                    onNonExistingCategoryAction = { categoryId ->
                        throw CategoryWithGivenIdDoesNotExist(categoryId)
                    }
                )
            }
            .also { expensePublisher.publishExpenseAddedEvent(it.toExpenseAddedEvent()) }

    override fun remove(expenseId: ExpenseId) {
        val expense =
            expenseRepository.getById(expenseId) ?: throw ExpenseNotFoundException(expenseId)
        expenseRepository.remove(expenseId)
        expensePublisher.publishExpenseRemovedEvent(expense.toExpenseRemovedEvent())
    }

    override fun getAll(): List<Expense> =
        expenseRepository.getAllExpenses()

    private fun AddExpenseParameters.toNewExpense(): Expense =
        Expense(
            id = ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )

    private fun Expense.toExpenseAddedEvent(): ExpenseAddedEvent =
        ExpenseAddedEvent(
            expenseId = id,
            categoryId = categoryId,
            amount = amount,
            paidAt = paidAt,
        )

    private fun Expense.toExpenseRemovedEvent(): ExpenseRemovedEvent =
        ExpenseRemovedEvent(
            expenseId = id,
            categoryId = categoryId,
        )

}

class ExpenseNotFoundException(expenseId: ExpenseId) :
    RuntimeException("Expense with id ${expenseId.id} does not exist")

class CategoryWithGivenIdDoesNotExist(categoryId: CategoryId) :
    RuntimeException("Category with id ${categoryId.id} does not exist")

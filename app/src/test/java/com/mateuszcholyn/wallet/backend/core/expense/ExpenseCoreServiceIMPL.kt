package com.mateuszcholyn.wallet.backend.core.expense

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.backend.events.ExpenseUpdatedEvent
import com.mateuszcholyn.wallet.randomUUID


class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
) : ExpenseCoreServiceAPI {
    override fun add(addExpenseParameters: AddExpenseParameters): Expense =
        addExpenseParameters
            .toNewExpense()
            .let { expenseRepositoryFacade.save(it) }
            .also { expensePublisher.publishExpenseAddedEvent(it.toExpenseAddedEvent()) }

    override fun remove(expenseId: ExpenseId) {
        val expense = expenseRepositoryFacade.getByIdOrThrow(expenseId)
        expenseRepositoryFacade.remove(expenseId)
        expensePublisher.publishExpenseRemovedEvent(expense.toExpenseRemovedEvent())
    }

    override fun getAll(): List<Expense> =
        expenseRepositoryFacade.getAllExpenses()

    override fun update(updateExpenseParameters: Expense): Expense {
        val oldExpense = expenseRepositoryFacade.getByIdOrThrow(updateExpenseParameters.id)

        return oldExpense
            .updateUsing(updateExpenseParameters)
            .let { expenseRepositoryFacade.save(it) }
            .also {
                expensePublisher.publishExpenseUpdatedEvent(
                    it.toExpenseUpdatedEvent(oldExpense = oldExpense)
                )
            }
    }

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

    private fun Expense.updateUsing(updateExpenseParameters: Expense): Expense =
        this.copy(
            amount = updateExpenseParameters.amount,
            description = updateExpenseParameters.description,
            paidAt = updateExpenseParameters.paidAt,
            categoryId = updateExpenseParameters.categoryId,
        )

    private fun Expense.toExpenseUpdatedEvent(
        oldExpense: Expense,
    ): ExpenseUpdatedEvent =
        ExpenseUpdatedEvent(
            expenseId = id,
            oldCategoryId = oldExpense.categoryId,
            newCategoryId = categoryId,
            newAmount = amount,
            newPaidAt = paidAt,
        )
}

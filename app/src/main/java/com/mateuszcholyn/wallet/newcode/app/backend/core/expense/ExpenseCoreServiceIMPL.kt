package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseUpdatedEvent
import com.mateuszcholyn.wallet.util.randomUUID


class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
) : ExpenseCoreServiceAPI {
    override fun add(addExpenseParameters: AddExpenseParameters): ExpenseV2 =
        addExpenseParameters
            .toNewExpense()
            .let { expenseRepositoryFacade.save(it) }
            .also { expensePublisher.publishExpenseAddedEvent(it.toExpenseAddedEvent()) }

    override fun remove(expenseId: ExpenseId) {
        val expense = expenseRepositoryFacade.getByIdOrThrow(expenseId)
        expenseRepositoryFacade.remove(expenseId)
        expensePublisher.publishExpenseRemovedEvent(expense.toExpenseRemovedEvent())
    }

    override fun getAll(): List<ExpenseV2> =
        expenseRepositoryFacade.getAllExpenses()

    override fun update(updateExpenseParameters: ExpenseV2): ExpenseV2 {
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

    private fun AddExpenseParameters.toNewExpense(): ExpenseV2 =
        ExpenseV2(
            id = ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )

    private fun ExpenseV2.toExpenseAddedEvent(): ExpenseAddedEvent =
        ExpenseAddedEvent(
            expenseId = id,
            categoryId = categoryId,
            amount = amount,
            paidAt = paidAt,
        )

    private fun ExpenseV2.toExpenseRemovedEvent(): ExpenseRemovedEvent =
        ExpenseRemovedEvent(
            expenseId = id,
            categoryId = categoryId,
        )

    private fun ExpenseV2.updateUsing(updateExpenseParameters: ExpenseV2): ExpenseV2 =
        this.copy(
            amount = updateExpenseParameters.amount,
            description = updateExpenseParameters.description,
            paidAt = updateExpenseParameters.paidAt,
            categoryId = updateExpenseParameters.categoryId,
        )

    private fun ExpenseV2.toExpenseUpdatedEvent(
        oldExpense: ExpenseV2,
    ): ExpenseUpdatedEvent =
        ExpenseUpdatedEvent(
            expenseId = id,
            oldCategoryId = oldExpense.categoryId,
            newCategoryId = categoryId,
            newAmount = amount,
            newPaidAt = paidAt,
        )
}

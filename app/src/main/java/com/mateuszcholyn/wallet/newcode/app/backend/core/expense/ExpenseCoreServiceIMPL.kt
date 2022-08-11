package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseAddedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseRemovedEvent
import com.mateuszcholyn.wallet.newcode.app.backend.events.ExpenseUpdatedEvent
import com.mateuszcholyn.wallet.util.randomUUID


class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
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
        val oldExpense = expenseRepositoryFacade.getByIdOrThrow(updateExpenseParameters.expenseId)

        return oldExpense
            .updateUsing(updateExpenseParameters)
            .let { expenseRepositoryFacade.save(it) }
            .also {
                expensePublisher.publishExpenseUpdatedEvent(
                    it.toExpenseUpdatedEvent(oldExpense = oldExpense)
                )
            }
    }

    override fun getByIdOrThrow(expenseId: ExpenseId): ExpenseV2WithCategory {
        val expense = expenseRepositoryFacade.getByIdOrThrow(expenseId)
        val category = categoryCoreServiceAPI.getByIdOrThrow(expense.categoryId)

        return ExpenseV2WithCategory(
            expenseId = expenseId,
            amount = expense.amount,
            description = expense.description,
            paidAt = expense.paidAt,
            categoryId = category.id,
            categoryName = category.name,
        )
    }

    private fun AddExpenseParameters.toNewExpense(): ExpenseV2 =
        ExpenseV2(
            expenseId = ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )

    private fun ExpenseV2.toExpenseAddedEvent(): ExpenseAddedEvent =
        ExpenseAddedEvent(
            expenseId = expenseId,
            categoryId = categoryId,
            amount = amount,
            paidAt = paidAt,
            description = description,
        )

    private fun ExpenseV2.toExpenseRemovedEvent(): ExpenseRemovedEvent =
        ExpenseRemovedEvent(
            expenseId = expenseId,
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
            expenseId = expenseId,
            oldCategoryId = oldExpense.categoryId,
            newCategoryId = categoryId,
            newAmount = amount,
            newPaidAt = paidAt,
            newDescription = description,
        )
}

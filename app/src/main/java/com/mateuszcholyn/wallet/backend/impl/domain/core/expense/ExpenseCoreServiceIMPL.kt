package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseWithCategory
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID

class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val transactionManager: TransactionManager,
) : ExpenseCoreServiceAPI {
    override suspend fun add(addExpenseParameters: AddExpenseParameters): Expense =
        transactionManager.runInTransaction {
            addExpenseParameters
                .toNewExpense()
                .let { expenseRepositoryFacade.create(it) }
                .also { expensePublisher.publishExpenseAddedEvent(it.toExpenseAddedEvent()) }
        }

    override suspend fun remove(expenseId: ExpenseId) {
        transactionManager.runInTransaction {
            val expense = expenseRepositoryFacade.getByIdOrThrow(expenseId)
            expenseRepositoryFacade.remove(expenseId)
            expensePublisher.publishExpenseRemovedEvent(expense.toExpenseRemovedEvent())
        }
    }

    override suspend fun getAll(): List<Expense> =
        expenseRepositoryFacade.getAllExpenses()

    override suspend fun update(updateExpenseParameters: Expense): Expense =
        transactionManager.runInTransaction {
            val oldExpense = expenseRepositoryFacade.getByIdOrThrow(updateExpenseParameters.expenseId)

            oldExpense
                .updateUsing(updateExpenseParameters)
                .let { expenseRepositoryFacade.update(it) }
                .also {
                    expensePublisher.publishExpenseUpdatedEvent(
                        it.toExpenseUpdatedEvent(oldExpense = oldExpense)
                    )
                }
        }

    override suspend fun getExpenseWithCategoryDetails(expenseId: ExpenseId): ExpenseWithCategory {
        val expense = expenseRepositoryFacade.getByIdOrThrow(expenseId)
        val category = categoryCoreServiceAPI.getByIdOrThrow(expense.categoryId)

        return ExpenseWithCategory(
            expenseId = expenseId,
            amount = expense.amount,
            description = expense.description,
            paidAt = expense.paidAt,
            categoryId = category.id,
            categoryName = category.name,
        )
    }

    override suspend fun getById(expenseId: ExpenseId): Expense? =
        expenseRepositoryFacade.getById(expenseId)

    override suspend fun removeAll() {
        transactionManager.runInTransaction {
            expenseRepositoryFacade.removeAll()
        }
    }

    private fun AddExpenseParameters.toNewExpense(): Expense =
        Expense(
            expenseId = this.expenseId ?: ExpenseId(randomUUID()),
            amount = amount,
            description = description,
            paidAt = paidAt,
            categoryId = categoryId,
        )

    private fun Expense.toExpenseAddedEvent(): ExpenseAddedEvent =
        ExpenseAddedEvent(
            expenseId = expenseId,
            categoryId = categoryId,
            amount = amount,
            paidAt = paidAt,
            description = description,
        )

    private fun Expense.toExpenseRemovedEvent(): ExpenseRemovedEvent =
        ExpenseRemovedEvent(
            expenseId = expenseId,
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
            expenseId = expenseId,
            oldCategoryId = oldExpense.categoryId,
            newCategoryId = categoryId,
            newAmount = amount,
            newPaidAt = paidAt,
            newDescription = description,
        )
}

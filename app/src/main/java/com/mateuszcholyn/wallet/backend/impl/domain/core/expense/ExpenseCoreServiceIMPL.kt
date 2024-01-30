package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.frontend.domain.usecase.transactionManager.TransactionManager
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID

class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val transactionManager: TransactionManager,
) : ExpenseCoreServiceAPI {
    override suspend fun add(addExpenseParameters: AddExpenseParameters): ExpenseV2 =
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

    override suspend fun getAll(): List<ExpenseV2> =
        expenseRepositoryFacade.getAllExpenses()

    override suspend fun update(updateExpenseParameters: ExpenseV2): ExpenseV2 =
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

    override suspend fun getExpenseWithCategoryDetails(expenseId: ExpenseId): ExpenseV2WithCategory {
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

    override suspend fun getById(expenseId: ExpenseId): ExpenseV2? =
        expenseRepositoryFacade.getById(expenseId)

    override suspend fun removeAll() {
        transactionManager.runInTransaction {
            expenseRepositoryFacade.removeAll()
        }
    }

    private fun AddExpenseParameters.toNewExpense(): ExpenseV2 =
        ExpenseV2(
            expenseId = this.expenseId ?: ExpenseId(randomUUID()),
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

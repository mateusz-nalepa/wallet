package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2WithCategory
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID


interface TransactionManager {
    fun <T> executeInTransaction(block: () -> T): T
}

class DefaultTransactionManager(
    private val dbHelper: SupportSQLiteOpenHelper,
) : TransactionManager {
    override fun <T> executeInTransaction(block: () -> T): T {
        val db = dbHelper.writableDatabase
        db.beginTransaction()
        try {
            val result = block.invoke()
            db.setTransactionSuccessful()
            return result
        } catch (e: Exception) {
            throw e
        } finally {
            db.endTransaction()
        }
    }
}


class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
//    private val transactionManager: TransactionManager,
) : ExpenseCoreServiceAPI {
    // TODO: ogólnie tutaj powinna być ta transakcja XD
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

    override fun removeAll() {
        expenseRepositoryFacade.removeAll()
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

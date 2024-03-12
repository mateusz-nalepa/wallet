package com.mateuszcholyn.wallet.backend.impl.domain.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.category.SubCategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.AddExpenseParameters
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseCoreServiceAPI
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.impl.domain.transaction.TransactionManager
import com.mateuszcholyn.wallet.util.randomuuid.randomUUID

class ExpenseCoreServiceIMPL(
    private val expenseRepositoryFacade: ExpenseRepositoryFacade,
    private val expensePublisher: ExpensePublisher,
    private val categoryCoreServiceAPI: CategoryCoreServiceAPI,
    private val transactionManager: TransactionManager,
) : ExpenseCoreServiceAPI {
    // to jest dobrze ogarnięte xd
    override suspend fun add(addExpenseParameters: AddExpenseParameters): Expense =
        transactionManager.runInTransaction {
            addExpenseParameters
                .toNewExpense()
                .let { expenseRepositoryFacade.create(it) }
                .also {
                    expensePublisher.publishExpenseAddedEvent(
                        it.toExpenseAddedEvent(getCategoryPaidForCategory(it.categoryId))
                    )
                }
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

            val oldExpense =
                expenseRepositoryFacade.getByIdOrThrow(updateExpenseParameters.expenseId)

            oldExpense
                .updateUsing(updateExpenseParameters)
                .let { expenseRepositoryFacade.update(it) }
                .also {
                    expensePublisher.publishExpenseUpdatedEvent(
                        it.toExpenseUpdatedEvent(
                            oldCategoryPair = getCategoryPaidForCategory(oldExpense.categoryId),
                            newCategoryPair = getCategoryPaidForCategory(updateExpenseParameters.categoryId),
                        )
                    )
                }
        }

    // TODO this should be in category repo?
    private suspend fun getCategoryPaidForCategory(categoryId: CategoryId): CategoryPair {
        val category = categoryCoreServiceAPI.getByIdOrThrow(categoryId)

        return resolveCategoryIdAndSubCategoryId(
            categoryId = category.id,
            parentCategoryId = category.parentCategory?.id,
        )
    }

    override suspend fun getById(expenseId: ExpenseId): Expense? =
        expenseRepositoryFacade.getById(expenseId)

    override suspend fun getByIdOrThrow(expenseId: ExpenseId): Expense =
        expenseRepositoryFacade.getByIdOrThrow(expenseId)

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

    private fun Expense.toExpenseAddedEvent(
        categoryPair: CategoryPair,
    ): ExpenseAddedEvent =
        ExpenseAddedEvent(
            expenseId = expenseId,
            categoryId = categoryPair,
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
        oldCategoryPair: CategoryPair,
        newCategoryPair: CategoryPair,
    ): ExpenseUpdatedEvent =
        ExpenseUpdatedEvent(
            expenseId = expenseId,
            oldCategoryId = oldCategoryPair,
            newCategoryId = newCategoryPair,
            newAmount = amount,
            newPaidAt = paidAt,
            newDescription = description,
        )
}

fun resolveCategoryIdAndSubCategoryId(
    categoryId: CategoryId,
    parentCategoryId: CategoryId?,
): CategoryPair =
    if (parentCategoryId != null) { // there is subcategory
        CategoryPair(
            categoryId = parentCategoryId,
            subCategoryId = SubCategoryId(categoryId.id),
        )
    } else {
        CategoryPair(
            categoryId = categoryId,
            subCategoryId = null,
        )
    }


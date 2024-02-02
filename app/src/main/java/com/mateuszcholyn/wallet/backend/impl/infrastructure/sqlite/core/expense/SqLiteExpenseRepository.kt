package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.Expense
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepository
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

class SqLiteExpenseRepository(
    private val expenseDao: ExpenseDao,
    private val dispatcherProvider: DispatcherProvider,
) : ExpenseRepository {

    override suspend fun create(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): Expense =
        withContext(dispatcherProvider.provideIODispatcher()) {
            try {
                expense
                    .toEntity()
                    .also { expenseDao.create(it) }
                    .toDomain()
            } catch (t: Throwable) {
                onNonExistingCategoryAction.invoke(expense.categoryId)
                throw t
            }
        }

    override suspend fun update(
        expense: Expense,
        onNonExistingCategoryAction: (CategoryId) -> Unit
    ): Expense =
        withContext(dispatcherProvider.provideIODispatcher()) {
            try {
                expense
                    .toEntity()
                    .also { expenseDao.update(it) }
                    .toDomain()
            } catch (t: Throwable) {
                onNonExistingCategoryAction.invoke(expense.categoryId)
                throw t
            }
        }

    override suspend fun getAllExpenses(): List<Expense> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            expenseDao
                .getAll()
                .map { it.toDomain() }
        }

    override suspend fun getById(expenseId: ExpenseId): Expense? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            expenseDao
                .getByExpenseId(expenseId.id)
                ?.toDomain()
        }

    override suspend fun remove(expenseId: ExpenseId) {
        withContext(dispatcherProvider.provideIODispatcher()) {
            expenseDao.remove(expenseId.id)
        }
    }

    override suspend fun removeAllExpenses() {
        withContext(dispatcherProvider.provideIODispatcher()) {

            expenseDao.removeAll()
        }
    }
}

private fun Expense.toEntity(): ExpenseEntity =
    ExpenseEntity(
        expenseId = expenseId.id,
        amount = amount,
        description = description,
        paidAt = paidAt,
        fkCategoryId = categoryId.id,
    )

private fun ExpenseEntity.toDomain(): Expense =
    Expense(
        expenseId = ExpenseId(expenseId),
        amount = amount,
        description = description,
        paidAt = paidAt,
        categoryId = CategoryId(fkCategoryId),
    )

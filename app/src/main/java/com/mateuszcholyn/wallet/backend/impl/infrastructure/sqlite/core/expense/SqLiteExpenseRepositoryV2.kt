package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import com.mateuszcholyn.wallet.backend.api.core.category.CategoryId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseId
import com.mateuszcholyn.wallet.backend.api.core.expense.ExpenseV2
import com.mateuszcholyn.wallet.backend.impl.domain.core.expense.ExpenseRepositoryV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.coroutineDispatcher.DispatcherProvider
import kotlinx.coroutines.withContext

class SqLiteExpenseRepositoryV2(
    private val expenseV2Dao: ExpenseV2Dao,
    private val dispatcherProvider: DispatcherProvider,
) : ExpenseRepositoryV2 {

    override suspend fun create(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): ExpenseV2 =
        withContext(dispatcherProvider.provideIODispatcher()) {
            try {
                expense
                    .toEntity()
                    .also { expenseV2Dao.create(it) }
                    .toDomain()
            } catch (t: Throwable) {
                onNonExistingCategoryAction.invoke(expense.categoryId)
                throw t
            }
        }

    override suspend fun update(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit
    ): ExpenseV2 =
        withContext(dispatcherProvider.provideIODispatcher()) {
            try {
                expense
                    .toEntity()
                    .also { expenseV2Dao.update(it) }
                    .toDomain()
            } catch (t: Throwable) {
                onNonExistingCategoryAction.invoke(expense.categoryId)
                throw t
            }
        }

    override suspend fun getAllExpenses(): List<ExpenseV2> =
        withContext(dispatcherProvider.provideIODispatcher()) {
            expenseV2Dao
                .getAll()
                .map { it.toDomain() }
        }

    override suspend fun getById(expenseId: ExpenseId): ExpenseV2? =
        withContext(dispatcherProvider.provideIODispatcher()) {
            expenseV2Dao
                .getByExpenseId(expenseId.id)
                ?.toDomain()
        }

    override suspend fun remove(expenseId: ExpenseId) {
        withContext(dispatcherProvider.provideIODispatcher()) {
            expenseV2Dao.remove(expenseId.id)
        }
    }

    override suspend fun removeAllExpenses() {
        withContext(dispatcherProvider.provideIODispatcher()) {

            expenseV2Dao.removeAll()
        }
    }
}

private fun ExpenseV2.toEntity(): ExpenseEntityV2 =
    ExpenseEntityV2(
        expenseId = expenseId.id,
        amount = amount,
        description = description,
        paidAt = paidAt,
        fkCategoryId = categoryId.id,
    )

private fun ExpenseEntityV2.toDomain(): ExpenseV2 =
    ExpenseV2(
        expenseId = ExpenseId(expenseId),
        amount = amount,
        description = description,
        paidAt = paidAt,
        categoryId = CategoryId(fkCategoryId),
    )

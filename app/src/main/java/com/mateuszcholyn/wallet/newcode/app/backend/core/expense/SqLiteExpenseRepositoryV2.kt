package com.mateuszcholyn.wallet.newcode.app.backend.core.expense

import com.mateuszcholyn.wallet.config.newDatabase.ExpenseEntityV2
import com.mateuszcholyn.wallet.config.newDatabase.ExpenseV2Dao
import com.mateuszcholyn.wallet.newcode.app.backend.core.category.CategoryId

class SqLiteExpenseRepositoryV2(
    private val expenseV2Dao: ExpenseV2Dao,
) : ExpenseRepositoryV2 {
    override fun save(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ): ExpenseV2 =
        expense
            .also { saveOrThrow(it, onNonExistingCategoryAction) }

    private fun saveOrThrow(
        expense: ExpenseV2,
        onNonExistingCategoryAction: (CategoryId) -> Unit,
    ) {
        try {
            expense
                .toEntity()
                .also { expenseV2Dao.save(it) }
        } catch (t: Throwable) {
            onNonExistingCategoryAction.invoke(expense.categoryId)
        }
    }

    override fun getAllExpenses(): List<ExpenseV2> =
        expenseV2Dao
            .getAll()
            .map { it.toDomain() }

    override fun getById(expenseId: ExpenseId): ExpenseV2? =
        expenseV2Dao
            .getByExpenseId(expenseId.id)
            ?.toDomain()

    override fun remove(expenseId: ExpenseId) {
        expenseV2Dao.remove(expenseId.id)
    }

    override fun removeAllExpenses() {
        expenseV2Dao.removeAll()
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

package com.mateuszcholyn.wallet.infrastructure.expense

import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.infrastructure.category.toDomain

class SqLiteExpenseRepository(
        private val expenseDao: ExpenseDao,
) : ExpenseRepository {

    private val expenseQueriesHelper = ExpenseQueriesHelper()

    override fun getById(expenseId: Long): Expense {
        return expenseDao
                .getExpenseWithCategory(expenseId)
                .toDomain()
    }


    override fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return expenseDao
                .getAll(expenseQueriesHelper.prepareExpenseSearchQuery(expenseSearchCriteria))
                .map { it.toDomain() }
    }

    override fun remove(expenseId: Long): Boolean {
        return expenseDao.remove(expenseId) == 1
    }

    override fun add(expense: Expense): Expense {
        return expense
                .toEntity()
                .let { expenseDao.add(it) }
                .let { expense.copy(id = it) }
    }

    override fun update(expense: Expense): Expense {
        expense
                .toEntity()
                .let { expenseDao.update(it) }

        return expenseDao
                .getExpenseWithCategory(expense.idOrThrow())
                .toDomain()
    }
}

fun Expense.toEntity(): ExpenseEntity {
    val id = id
    return ExpenseEntity(
            expenseId = if (id != -1L) id else null,
            amount = amount.toDouble(),
            description = description,
            date = date,
            fkCategoryId = category.id
    )
}

fun ExpenseWithCategory.toDomain(): Expense {
    val expense = this.expenseEntity
    val category = this.categoryEntity
    return Expense(
            id = expense.expenseId!!,
            amount = expense.amount!!.toString().toBigDecimal(),
            description = expense.description!!,
            date = expense.date!!,
            category = category.toDomain()
    )
}
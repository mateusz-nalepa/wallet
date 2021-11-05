package com.mateuszcholyn.wallet.infrastructure.expense

import com.mateuszcholyn.wallet.domain.expense.AverageSearchCriteria
import com.mateuszcholyn.wallet.domain.expense.Expense
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseSearchCriteria
import com.mateuszcholyn.wallet.infrastructure.category.toDomain
import com.mateuszcholyn.wallet.util.asPrinteableAmount
import java.time.LocalDateTime

class SqLiteExpenseRepository(
    private val expenseDao: ExpenseDao,
) : ExpenseRepository {

    private val expenseQueriesHelper = ExpenseQueriesHelper()

    override fun getAll(): List<Expense> {
        TODO("Not yet implemented")
    }

    override fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return expenseDao
            .getAll(expenseQueriesHelper.prepareExpenseSearchQuery(expenseSearchCriteria))
            .map { it.toDomain() }
    }

    override fun getExpenseWithCategory(expenseId: Long): ExpenseWithCategory {
        TODO("Not yet implemented")
    }

    override fun remove(expenseId: Long): Boolean {
        return expenseDao.remove(expenseId) == 1
    }

    override fun moneySpentBetween(start: LocalDateTime, end: LocalDateTime): Double {
        return expenseDao.moneySpentBetween(start, end)
    }

    override fun averageAmount(averageSearchCriteria: AverageSearchCriteria): Double {
        return expenseDao
            .averageAmount(expenseQueriesHelper.prepareAverageSearchQuery(averageSearchCriteria))
            .asPrinteableAmount()
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

        return expenseDao.getExpenseWithCategory(expense.id)
            .toDomain()
    }
}

fun Expense.toEntity(): ExpenseEntity {
    val id = id
    return ExpenseEntity(
        expenseId = if (id != -1L) id else null,
        amount = amount,
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
        amount = expense.amount!!,
        description = expense.description!!,
        date = expense.date!!,
        category = category.toDomain()
    )
}
package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity

@Dao
interface ExpenseDao {

    @Query(
            """SELECT Expense.*, Category.*
                    FROM Expense
                    INNER JOIN Category ON Expense.fk_category_id = Category.category_id
                    WHERE Expense.expense_id = :expenseId"""
    )
    fun getExpenseWithCategory(expenseId: Long): ExpenseWithCategory

    @Query("delete from Expense where expense_id = :expenseId")
    fun remove(expenseId: Long): Int

    @RawQuery(observedEntities = [ExpenseEntity::class, CategoryEntity::class])
    fun getAll(query: SupportSQLiteQuery): List<ExpenseWithCategory>

    @Insert
    fun add(expenseEntity: ExpenseEntity): Long

    @Update
    fun update(expenseEntity: ExpenseEntity): Int

}

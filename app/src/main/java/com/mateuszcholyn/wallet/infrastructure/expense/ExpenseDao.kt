package com.mateuszcholyn.wallet.infrastructure.expense

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity
import org.joda.time.LocalDateTime

@Dao
interface ExpenseDao {

    @Query("select * from Expense")
    fun getAll(): List<ExpenseEntity>

    @Query(
        """SELECT Expense.*, Category.*
                    FROM Expense
                    INNER JOIN Category ON Expense.fk_category_id = Category.category_id
                    WHERE Expense.expense_id = :expenseId"""
    )
    fun getExpenseWithCategory(expenseId: Long): ExpenseWithCategory

    @Query("delete from Expense where expense_id = :expenseId")
    fun remove(expenseId: Long): Int

    @Query("SELECT sum(amount) FROM Expense where date >= :start and date <= :end")
    fun moneySpentBetween(start: LocalDateTime, end: LocalDateTime): Double

    @RawQuery(observedEntities = [ExpenseEntity::class, CategoryEntity::class])
    fun averageAmount(query: SupportSQLiteQuery): Double

    @RawQuery(observedEntities = [ExpenseEntity::class, CategoryEntity::class])
    fun getAll(query: SupportSQLiteQuery): List<ExpenseWithCategory>

    @Insert
    fun add(expenseEntity: ExpenseEntity): Long

    @Update
    fun update(expenseEntity: ExpenseEntity): Int

}

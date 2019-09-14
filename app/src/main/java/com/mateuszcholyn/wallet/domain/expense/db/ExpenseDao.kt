package com.mateuszcholyn.wallet.domain.expense.db

import android.arch.persistence.db.SupportSQLiteQuery
import android.arch.persistence.room.*
import com.mateuszcholyn.wallet.domain.category.db.model.Category
import com.mateuszcholyn.wallet.domain.expense.db.model.Expense
import com.mateuszcholyn.wallet.domain.expense.db.model.ExpenseWithCategory

@Dao
interface ExpenseDao {

    @Query("select * from Expense")
    fun getAll(): List<Expense>

    @Query("""SELECT Expense.*, Category.*
                    FROM Expense
                    INNER JOIN Category ON Expense.fk_category_id = Category.category_id
                    WHERE Expense.expense_id = :expenseId""")
    fun getExpenseWithCategory(expenseId: Long): ExpenseWithCategory

    @Query("delete from Expense where expense_id = :expenseId")
    fun remove(expenseId: Long): Int

    @RawQuery(observedEntities = [Expense::class, Category::class])
    fun averageAmount(query: SupportSQLiteQuery): Double

    @RawQuery(observedEntities = [Expense::class, Category::class])
    fun getAll(query: SupportSQLiteQuery): List<ExpenseWithCategory>

    @Insert
    fun add(expense: Expense): Long

    @Update
    fun update(expense: Expense): Int

}

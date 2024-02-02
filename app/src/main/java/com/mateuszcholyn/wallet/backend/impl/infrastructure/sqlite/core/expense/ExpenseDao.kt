package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ExpenseDao {

    @Insert
    suspend fun create(expenseEntity: ExpenseEntity)

    @Update
    suspend fun update(expenseEntity: ExpenseEntity)

    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<ExpenseEntity>

    @Query(
        """SELECT * 
              FROM expenses
              WHERE expense_id =:expenseId
              """
    )
    suspend fun getByExpenseId(expenseId: String): ExpenseEntity?

    @Query("delete from expenses where expense_id = :expenseId")
    suspend fun remove(expenseId: String): Int

    @Query("delete from expenses")
    suspend fun removeAll(): Int

}


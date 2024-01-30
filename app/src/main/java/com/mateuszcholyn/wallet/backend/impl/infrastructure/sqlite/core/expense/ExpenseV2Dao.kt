package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ExpenseV2Dao {

    @Insert
    suspend fun create(expenseEntityV2: ExpenseEntityV2)

    @Update
    suspend fun update(expenseEntityV2: ExpenseEntityV2)

    @Query("SELECT * FROM expenses")
    suspend fun getAll(): List<ExpenseEntityV2>

    @Query(
        """SELECT * 
              FROM expenses
              WHERE expense_id =:expenseId
              """
    )
    suspend fun getByExpenseId(expenseId: String): ExpenseEntityV2?

    @Query("delete from expenses where expense_id = :expenseId")
    suspend fun remove(expenseId: String): Int

    @Query("delete from expenses")
    suspend fun removeAll(): Int

}


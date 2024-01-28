package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface ExpenseV2Dao {

    @Insert
    fun create(expenseEntityV2: ExpenseEntityV2)

    @Update
    fun update(expenseEntityV2: ExpenseEntityV2)

    @Query("SELECT * FROM expenses")
    fun getAll(): List<ExpenseEntityV2>

    @Query(
        """SELECT * 
              FROM expenses
              WHERE expense_id =:expenseId
              """
    )
    fun getByExpenseId(expenseId: String): ExpenseEntityV2?

    @Query("delete from expenses where expense_id = :expenseId")
    fun remove(expenseId: String): Int

    @Query("delete from expenses")
    fun removeAll(): Int

}


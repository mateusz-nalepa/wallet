package com.mateuszcholyn.wallet.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mateuszcholyn.wallet.database.model.Expense

@Dao
interface ExpenseDao {

    @Query("select * from Expense")
    fun getAll(): List<Expense>

    @Insert
    fun insert(expense: Expense): Long

}
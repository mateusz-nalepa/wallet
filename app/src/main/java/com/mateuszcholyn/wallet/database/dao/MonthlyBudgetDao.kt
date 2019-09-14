package com.mateuszcholyn.wallet.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mateuszcholyn.wallet.database.model.MonthlyBudget

@Dao
interface MonthlyBudgetDao {

    @Query("select * from MonthlyBudget")
    fun getAll(): List<MonthlyBudget>

    @Insert
    fun insert(monthlyBudget: MonthlyBudget) : Long

}
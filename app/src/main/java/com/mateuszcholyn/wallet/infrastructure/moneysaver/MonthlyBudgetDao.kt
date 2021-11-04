package com.mateuszcholyn.wallet.infrastructure.moneysaver

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MonthlyBudgetDao {

    @Query("select * from MonthlyBudget")
    fun getAll(): List<MonthlyBudgetEntity>

    @Query("select * from MonthlyBudget where year = :year and month = :month")
    fun get(year: Int, month: Int): MonthlyBudgetEntity?

    @Insert
    fun insert(monthlyBudget: MonthlyBudgetEntity): Long
}
package com.mateuszcholyn.wallet.domain.moneysaver.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mateuszcholyn.wallet.domain.moneysaver.db.model.MonthlyBudget

@Dao
interface MonthlyBudgetDao {

    @Query("select * from MonthlyBudget")
    fun getAll(): List<MonthlyBudget>

    @Query("select * from MonthlyBudget where year = :year and month = :month")
    fun get(year: Int, month: Int): MonthlyBudget?

    @Insert
    fun insert(monthlyBudget: MonthlyBudget): Long

}
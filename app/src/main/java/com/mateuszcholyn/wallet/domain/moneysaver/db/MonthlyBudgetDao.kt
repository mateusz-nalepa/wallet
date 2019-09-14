package com.mateuszcholyn.wallet.domain.moneysaver.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mateuszcholyn.wallet.domain.moneysaver.db.model.MonthlyBudget

@Dao
interface MonthlyBudgetDao {

    @Query("select * from MonthlyBudget")
    fun getAll(): List<MonthlyBudget>

    @Insert
    fun insert(monthlyBudget: MonthlyBudget) : Long

}
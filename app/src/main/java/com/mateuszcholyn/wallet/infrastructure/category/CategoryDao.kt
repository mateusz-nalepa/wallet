package com.mateuszcholyn.wallet.infrastructure.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseEntity

@Dao
interface CategoryDao {

    @Query("select * from Category")
    fun getAll(): List<CategoryEntity>

    @RawQuery(observedEntities = [ExpenseEntity::class, CategoryEntity::class])
    fun getAllOrderByUsageDesc(query: SupportSQLiteQuery): List<CategoryEntity>

    @Query("select * from Category where name = :name")
    fun getCategoryByName(name: String): CategoryEntity

    @Query("delete from Category where name = :name")
    fun remove(name: String): Int

    @Insert
    fun add(categoryEntity: CategoryEntity): Long

}
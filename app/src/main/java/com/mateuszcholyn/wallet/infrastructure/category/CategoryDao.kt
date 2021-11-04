package com.mateuszcholyn.wallet.infrastructure.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface CategoryDao {

    @Query("select * from Category")
    fun getAll(): List<CategoryEntity>

    @Query("select * from Category where name = :name")
    fun getCategoryByName(name: String): CategoryEntity

    @Query("delete from Category where name = :name")
    fun remove(name: String): Int

    @Insert
    fun add(categoryEntity: CategoryEntity): Long

}
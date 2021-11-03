package com.mateuszcholyn.wallet.domain.category.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mateuszcholyn.wallet.domain.category.db.model.Category

@Dao
interface CategoryDao {

    @Query("select * from Category")
    fun getAll(): List<Category>

    @Query("select category_id from Category where name = :name")
    fun getCategoryIdByName(name: String): Long

    @Query("select * from Category where name = :name")
    fun getCategoryByName(name: String): Category

    @Query("delete from Category where name = :name")
    fun remove(name: String): Int

    @Insert
    fun add(category: Category): Long

}
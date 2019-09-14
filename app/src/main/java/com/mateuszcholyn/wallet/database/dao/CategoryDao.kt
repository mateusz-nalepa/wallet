package com.mateuszcholyn.wallet.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.mateuszcholyn.wallet.database.model.Category

@Dao
interface CategoryDao {

    @Query("select * from Category")
    fun getAll(): List<Category>

    @Insert
    fun insert(category: Category): Long

}
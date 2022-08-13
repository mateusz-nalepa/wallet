package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CategoryV2Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(categoryEntityV2: CategoryEntityV2)

    @Query("SELECT * FROM categories")
    fun getAll(): List<CategoryEntityV2>

    @Query(
        """SELECT * 
              FROM categories
              WHERE category_id =:categoryId
              """
    )
    fun getByCategoryId(categoryId: String): CategoryEntityV2?

    @Query("delete from categories where category_id = :categoryId")
    fun remove(categoryId: String): Int

    @Query("delete from categories")
    fun removeAll()
}

package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CategoryV2Dao {

    @Insert
    suspend fun create(categoryEntityV2: CategoryEntityV2)

    @Update
    suspend fun update(categoryEntityV2: CategoryEntityV2)

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<CategoryEntityV2>

    @Query(
        """SELECT * 
              FROM categories
              WHERE category_id =:categoryId
              """
    )
    suspend fun getByCategoryId(categoryId: String): CategoryEntityV2?

    @Query("delete from categories where category_id = :categoryId")
    suspend fun remove(categoryId: String): Int

    @Query("delete from categories")
    suspend fun removeAll()
}

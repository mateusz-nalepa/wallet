package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


@Dao
interface CategoryDao {

    @Insert
    suspend fun create(categoryEntity: CategoryEntity)

    @Update
    suspend fun update(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM categories")
    suspend fun getAll(): List<CategoryEntity>

    @Query(
        """SELECT * 
              FROM categories
              WHERE category_id =:categoryId
              """
    )
    suspend fun getByCategoryId(categoryId: String): CategoryEntity?

    @Query("delete from categories where category_id = :categoryId")
    suspend fun remove(categoryId: String): Int

    @Query("delete from categories")
    suspend fun removeAll()
}

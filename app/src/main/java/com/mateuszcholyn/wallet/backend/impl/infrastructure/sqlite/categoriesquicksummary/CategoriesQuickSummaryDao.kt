package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface CategoriesQuickSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(categoriesQuickSummaryEntity: CategoriesQuickSummaryEntity)

    @Query("SELECT * FROM categories_quick_summary")
    suspend fun getAll(): List<CategoriesQuickSummaryEntity>

    @Query(
        """SELECT * 
              FROM categories_quick_summary
              WHERE category_id =:categoryId
              """
    )
    suspend fun findByCategoryId(categoryId: String): CategoriesQuickSummaryEntity?

    @Query("delete from categories_quick_summary where category_id = :categoryId")
    suspend fun remove(categoryId: String): Int

    @Query("delete from categories_quick_summary")
    suspend fun removeAll()

}

package com.mateuszcholyn.wallet.config.newDatabase

import androidx.room.*


@Dao
interface CategoriesQuickSummaryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(categoriesQuickSummaryEntity: CategoriesQuickSummaryEntity)

    @Query("SELECT * FROM categories_quick_summary")
    fun getAll(): List<CategoriesQuickSummaryEntity>

    @Query(
        """SELECT * 
              FROM categories_quick_summary
              WHERE category_id =:categoryId
              """
    )
    fun findByCategoryId(categoryId: String): CategoriesQuickSummaryEntity?

    @Query("delete from categories_quick_summary where category_id = :categoryId")
    fun remove(categoryId: String): Int

}

@Entity(
    tableName = "categories_quick_summary",
    indices = [
        Index("category_id", unique = true),
    ]
)
data class CategoriesQuickSummaryEntity(

    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: String,

    @ColumnInfo(name = "number_of_expenses")
    val numberOfExpenses: Long,
)

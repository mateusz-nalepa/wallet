package com.mateuszcholyn.wallet.config.newDatabase

import androidx.room.*


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

@Entity(
    tableName = "categories",
    indices = [
        Index("name", unique = true),
        Index("category_id", unique = true),
    ]
)
data class CategoryEntityV2(

    @PrimaryKey
    @ColumnInfo(name = "category_id")
    val categoryId: String,

    @ColumnInfo(name = "name")
    val name: String
)

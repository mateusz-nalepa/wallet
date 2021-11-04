package com.mateuszcholyn.wallet.infrastructure.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "Category",
    indices = [
        Index("name", unique = true),
        Index("category_id", unique = true)]
)
data class CategoryEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "category_id")
    val categoryId: Long? = null,

    @ColumnInfo(name = "name")
    var name: String?
)
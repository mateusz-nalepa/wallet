package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


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

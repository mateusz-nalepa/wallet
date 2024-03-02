package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


const val CATEGORIES_TABLE_NAME = "categories"

const val CATEGORIES_TABLE_CATEGORY_NAME_COLUMN = "name"

const val CATEGORIES_TABLE_CATEGORY_ID_COLUMN = "category_id"


@Entity(
    tableName = CATEGORIES_TABLE_NAME,
    indices = [
        Index(CATEGORIES_TABLE_CATEGORY_ID_COLUMN, unique = true),
    ]
)
// TODO: add subcategory
data class CategoryEntity(

    @PrimaryKey
    @ColumnInfo(name = CATEGORIES_TABLE_CATEGORY_ID_COLUMN)
    val categoryId: String,

    @ColumnInfo(name = CATEGORIES_TABLE_CATEGORY_NAME_COLUMN)
    val name: String
)

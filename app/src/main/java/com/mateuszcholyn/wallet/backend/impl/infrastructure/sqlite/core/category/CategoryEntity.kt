package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


const val CATEGORIES_TABLE_NAME = "categories"

const val CATEGORIES_TABLE_CATEGORY_NAME_COLUMN = "name"

const val CATEGORIES_TABLE_CATEGORY_ID_COLUMN = "category_id"

const val CATEGORIES_TABLE_PARENT_CATEGORY_ID_COLUMN = "parent_category_id"

@Entity(
    tableName = CATEGORIES_TABLE_NAME,
    indices = [
        Index(CATEGORIES_TABLE_CATEGORY_ID_COLUMN, unique = true),
    ],
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = arrayOf(CATEGORIES_TABLE_CATEGORY_ID_COLUMN),
            childColumns = arrayOf(CATEGORIES_TABLE_PARENT_CATEGORY_ID_COLUMN),
            onDelete = ForeignKey.RESTRICT, // TODO: na pewno?
        )
    ]
)
// TODO: add subcategory
data class CategoryEntity(

    @PrimaryKey
    @ColumnInfo(name = CATEGORIES_TABLE_CATEGORY_ID_COLUMN)
    val categoryId: String,

    @ColumnInfo(name = CATEGORIES_TABLE_PARENT_CATEGORY_ID_COLUMN)
    val parentCategoryId: String?,

    @ColumnInfo(name = CATEGORIES_TABLE_CATEGORY_NAME_COLUMN)
    val name: String
)

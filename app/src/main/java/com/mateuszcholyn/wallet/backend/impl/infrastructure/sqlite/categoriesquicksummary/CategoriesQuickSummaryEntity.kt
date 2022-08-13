package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

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

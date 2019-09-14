package com.mateuszcholyn.wallet.domain.category.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Category",
        indices = [
            Index("name", unique = true),
            Index("category_id", unique = true)])
data class Category(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "category_id")
        val categoryId: Long? = null,

        @ColumnInfo(name = "name")
        var name: String?
)
package com.mateuszcholyn.wallet.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "Category")
data class Category(

        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,

        @ColumnInfo(name = "name")
        var name: String?
)
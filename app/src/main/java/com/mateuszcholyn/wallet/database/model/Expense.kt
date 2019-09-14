package com.mateuszcholyn.wallet.database.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import org.joda.time.LocalDateTime

@Entity(
        tableName = "Expense",
        foreignKeys = [
            ForeignKey(entity = Category::class,
                    parentColumns = arrayOf("id"),
                    childColumns = arrayOf("id"))
        ])
data class Expense(

        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,

        @ColumnInfo(name = "amount")
        var amount: Double?,

        @ColumnInfo(name = "description")
        var description: String?,

        @ColumnInfo(name = "date")
        var date: LocalDateTime?,

        @ColumnInfo(name = "category_id")
        var categoryId: Long?

)
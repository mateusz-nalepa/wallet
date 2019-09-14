package com.mateuszcholyn.wallet.domain.moneysaver.db.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "MonthlyBudget")
data class MonthlyBudget(
        @PrimaryKey(autoGenerate = true)
        val id: Long? = null,

        @ColumnInfo(name = "budget")
        var budget: Double?,

        @ColumnInfo(name = "year")
        var year: Int?,

        @ColumnInfo(name = "month")
        var month: Int?

)
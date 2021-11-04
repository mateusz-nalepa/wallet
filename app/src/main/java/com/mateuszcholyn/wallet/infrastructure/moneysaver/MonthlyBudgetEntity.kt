package com.mateuszcholyn.wallet.infrastructure.moneysaver

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "MonthlyBudget")
data class MonthlyBudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,

    @ColumnInfo(name = "budget")
    var budget: Double?,

    @ColumnInfo(name = "year")
    var year: Int?,

    @ColumnInfo(name = "month")
    var month: Int?

)
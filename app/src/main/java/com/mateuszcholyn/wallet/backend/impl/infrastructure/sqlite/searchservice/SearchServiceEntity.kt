package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import java.math.BigDecimal
import java.time.LocalDateTime

const val SEARCH_SERVICE_TABLE_NAME = "search_service"

const val EXPENSE_ID_FIELD_NAME = "expense_id"
const val CATEGORY_ID_FIELD_NAME = "category_id"
const val AMOUNT_FIELD_NAME = "amount"
const val PAID_AT_FIELD_NAME = "paid_at"
const val DESCRIPTION_FIELD_NAME = "description"

@Entity(
    tableName = SEARCH_SERVICE_TABLE_NAME,
    indices = [
        Index(EXPENSE_ID_FIELD_NAME, unique = true),
        Index(CATEGORY_ID_FIELD_NAME),
        Index(AMOUNT_FIELD_NAME),
        Index(PAID_AT_FIELD_NAME),
    ]
)
data class SearchServiceEntity(

    @PrimaryKey
    @ColumnInfo(name = EXPENSE_ID_FIELD_NAME)
    val expenseId: String,

    @ColumnInfo(name = CATEGORY_ID_FIELD_NAME)
    val categoryId: String,

    @ColumnInfo(name = AMOUNT_FIELD_NAME)
    val amount: BigDecimal,

    @ColumnInfo(name = PAID_AT_FIELD_NAME)
    val paidAt: LocalDateTime,

    @ColumnInfo(name = DESCRIPTION_FIELD_NAME)
    val description: String,
)

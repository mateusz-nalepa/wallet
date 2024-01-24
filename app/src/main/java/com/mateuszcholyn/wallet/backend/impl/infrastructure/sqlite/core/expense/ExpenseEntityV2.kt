package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryEntityV2
import java.math.BigDecimal
import java.time.Instant

const val EXPENSES_TABLE_NAME = "expenses"

const val EXPENSES_FK_CATEGORY_ID_NAME = "fk_category_id"

@Entity(
    tableName = EXPENSES_TABLE_NAME,
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntityV2::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf(EXPENSES_FK_CATEGORY_ID_NAME)
        )
    ],
    indices = [
        Index("expense_id", unique = true),
        Index(EXPENSES_FK_CATEGORY_ID_NAME, unique = false),
    ]
)
data class ExpenseEntityV2(

    @PrimaryKey
    @ColumnInfo(name = "expense_id")
    val expenseId: String,

    @ColumnInfo(name = "amount")
    val amount: BigDecimal,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "paid_at")
    val paidAt: Instant,

    @ColumnInfo(name = EXPENSES_FK_CATEGORY_ID_NAME)
    val fkCategoryId: String,
)

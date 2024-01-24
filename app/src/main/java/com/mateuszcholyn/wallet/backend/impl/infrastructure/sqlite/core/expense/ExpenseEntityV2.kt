package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryEntityV2
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity(
    tableName = "expenses",
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntityV2::class,
            parentColumns = arrayOf("category_id"),
            childColumns = arrayOf("fk_category_id")
        )
    ],
    indices = [
        Index("expense_id", unique = true),
        Index("fk_category_id", unique = false),
    ]
)
// TODO: fixme XD
// warning: fk_category_id column references a foreign key but it is not part of an index.
// This may trigger full table scans whenever parent table is modified so you are highly advised to create an index that covers
// this column.
data class ExpenseEntityV2(

    @PrimaryKey
    @ColumnInfo(name = "expense_id")
    val expenseId: String,

    @ColumnInfo(name = "amount")
    val amount: BigDecimal,

    @ColumnInfo(name = "description")
    val description: String,

    // TODO: użyj Instant, zamiast LocalDateTime XD
    @ColumnInfo(name = "paid_at")
    val paidAt: LocalDateTime,

    @ColumnInfo(name = "fk_category_id")
    val fkCategoryId: String,
)

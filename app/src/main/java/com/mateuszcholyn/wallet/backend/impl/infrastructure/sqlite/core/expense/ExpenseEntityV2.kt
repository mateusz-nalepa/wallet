package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense

import androidx.room.*
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
        // HODOR: add index for fk_category_id
        Index("expense_id", unique = true),
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
    val paidAt: LocalDateTime,

    @ColumnInfo(name = "fk_category_id")
    val fkCategoryId: String,
)

package com.mateuszcholyn.wallet.domain.expense.db.model

import androidx.room.*
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity
import org.joda.time.LocalDateTime

@Entity(
        tableName = "Expense",
        foreignKeys = [
            ForeignKey(entity = CategoryEntity::class,
                    parentColumns = arrayOf("category_id"),
                    childColumns = arrayOf("fk_category_id"))
        ],
        indices = [
            Index("expense_id", unique = true),
            Index("fk_category_id")
        ])
data class Expense(

        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "expense_id")
        val expenseId: Long? = null,

        @ColumnInfo(name = "amount")
        var amount: Double?,

        @ColumnInfo(name = "description")
        var description: String?,

        @ColumnInfo(name = "date")
        var date: LocalDateTime?,

        @ColumnInfo(name = "fk_category_id")
        var fkCategoryId: Long?

)
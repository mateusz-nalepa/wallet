package com.mateuszcholyn.wallet.domain.expense.db.model

import android.arch.persistence.room.*
import com.mateuszcholyn.wallet.domain.category.db.model.Category
import org.joda.time.LocalDateTime

@Entity(
        tableName = "Expense",
        foreignKeys = [
            ForeignKey(entity = Category::class,
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
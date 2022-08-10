package com.mateuszcholyn.wallet.config.newDatabase

import androidx.room.*
import java.math.BigDecimal
import java.time.LocalDateTime


@Dao
interface ExpenseV2Dao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(expenseEntityV2: ExpenseEntityV2)

    @Query("SELECT * FROM expenses")
    fun getAll(): List<ExpenseEntityV2>

    @Query(
        """SELECT * 
              FROM expenses
              WHERE expense_id =:expenseId
              """
    )
    fun getByExpenseId(expenseId: String): ExpenseEntityV2?

    @Query("delete from expenses where expense_id = :expenseId")
    fun remove(expenseId: String): Int

}

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
        Index("fk_category_id")
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

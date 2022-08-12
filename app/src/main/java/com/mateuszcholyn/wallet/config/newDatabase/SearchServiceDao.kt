package com.mateuszcholyn.wallet.config.newDatabase

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import java.math.BigDecimal
import java.time.LocalDateTime


@Dao
interface SearchServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(searchServiceEntity: SearchServiceEntity)

    @RawQuery(observedEntities = [SearchServiceEntity::class])
    fun getAll(query: SupportSQLiteQuery): List<SearchServiceEntity>

    @Query(
        """SELECT * 
              FROM search_service
              WHERE expense_id =:expenseId
              """
    )
    fun findByExpenseId(expenseId: String): SearchServiceEntity?

    @Query("delete from search_service where expense_id = :expenseId")
    fun remove(expenseId: String): Int

    @Query("delete from search_service")
    fun removeAll()

}

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

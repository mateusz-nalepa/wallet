package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery


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


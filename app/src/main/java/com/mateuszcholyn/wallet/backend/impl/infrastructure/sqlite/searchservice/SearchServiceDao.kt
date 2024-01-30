package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery


@Dao
interface SearchServiceDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun save(searchServiceEntity: SearchServiceEntity)

    @RawQuery(observedEntities = [SearchServiceEntity::class])
    suspend fun getAll(query: SupportSQLiteQuery): List<SearchServiceEntity>

    @Query(
        """SELECT * 
              FROM search_service
              WHERE expense_id =:expenseId
              """
    )
    suspend fun findByExpenseId(expenseId: String): SearchServiceEntity?

    @Query("delete from search_service where expense_id = :expenseId")
    suspend fun remove(expenseId: String): Int

    @Query("delete from search_service")
    suspend fun removeAll()

}


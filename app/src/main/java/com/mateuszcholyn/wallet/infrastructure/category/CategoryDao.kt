package com.mateuszcholyn.wallet.infrastructure.category

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.mateuszcholyn.wallet.infrastructure.expense.CategoryWithExpense
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseEntity

@Dao
interface CategoryDao {

    @RawQuery(observedEntities = [ExpenseEntity::class, CategoryEntity::class])
    fun getAllOrderByUsageDesc(query: SupportSQLiteQuery): List<CategoryEntity>

    @Query(
            """SELECT Category.*, Expense.*
                    FROM Category
                    LEFT JOIN Expense ON Expense.fk_category_id = Category.category_id"""
    )
    fun getAllDataFromDb(): List<CategoryWithExpense>

    @Query("select * from Category where name = :name")
    fun getCategoryByName(name: String): CategoryEntity

    @Query("delete from Category where name = :name")
    fun remove(name: String): Int

    @Insert
    fun add(categoryEntity: CategoryEntity): Long

}
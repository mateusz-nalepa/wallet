package com.mateuszcholyn.wallet.infrastructure.category

import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseEntity

@Dao
interface CategoryDao {

    @RawQuery(observedEntities = [ExpenseEntity::class, CategoryEntity::class])
    fun getAllOrderByUsageDesc(query: SupportSQLiteQuery): List<CategoryEntity>

    @Query(
        """SELECT count(Category.name)
                    FROM Category"""
    )
    fun count(): Int

    @Query(
        """SELECT Category.*
                    FROM Category"""
    )
    fun getAll(): List<CategoryEntity>

    @Query("delete from Category where category_id = :categoryId")
    fun remove(categoryId: Long): Int

    @Query("delete from Category")
    fun removeAll(): Int

    @Insert
    fun add(categoryEntity: CategoryEntity): Long

    @Update
    fun update(categoryEntity: CategoryEntity): Int

    @Query(
        """SELECT Category.*, Expense.*
                    FROM Category
                    LEFT JOIN Expense ON Expense.fk_category_id = Category.category_id"""
    )
    fun getAllCategoriesWithExpenses(): List<CategoryWithExpense>

}

data class CategoryWithExpense(

    @Embedded
    val categoryEntity: CategoryEntity,

    @Embedded
    val expenseEntity: ExpenseEntity? = null,
)
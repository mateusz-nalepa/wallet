package com.mateuszcholyn.wallet.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszcholyn.wallet.config.newDatabase.*
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseDao
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseEntity
import com.mateuszcholyn.wallet.infrastructure.moneysaver.MonthlyBudgetEntity
import com.mateuszcholyn.wallet.infrastructure.util.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.infrastructure.util.LocalDateTimeConverter


@Database(
    entities = [
        MonthlyBudgetEntity::class,
        CategoryEntity::class,
        ExpenseEntity::class
    ],
    version = 2,
)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
}


@Database(
    entities = [
        CategoriesQuickSummaryEntity::class,
        CategoryEntityV2::class,
        ExpenseEntityV2::class,
    ],
    version = 1,
)
@TypeConverters(
    value = [
        LocalDateTimeConverter::class,
        BigDecimalDoubleTypeConverter::class,
    ],
)
abstract class AppDatabaseV2 : RoomDatabase() {
    abstract fun categoriesQuickSummaryDao(): CategoriesQuickSummaryDao
    abstract fun categoryV2Dao(): CategoryV2Dao
    abstract fun expenseV2Dao(): ExpenseV2Dao
}

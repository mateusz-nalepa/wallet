package com.mateuszcholyn.wallet.config

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszcholyn.wallet.config.newDatabase.*
import com.mateuszcholyn.wallet.infrastructure.util.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.infrastructure.util.LocalDateTimeConverter


@Database(
    entities = [
        CategoriesQuickSummaryEntity::class,
        CategoryEntityV2::class,
        ExpenseEntityV2::class,
        SearchServiceEntity::class,
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
    abstract fun searchServiceDao(): SearchServiceDao
}

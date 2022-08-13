package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.CategoriesQuickSummaryDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.CategoriesQuickSummaryEntity
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.LocalDateTimeConverter
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryEntityV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryV2Dao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.ExpenseEntityV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.ExpenseV2Dao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice.SearchServiceDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice.SearchServiceEntity


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

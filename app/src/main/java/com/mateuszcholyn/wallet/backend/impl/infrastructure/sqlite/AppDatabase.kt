package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.CategoriesQuickSummaryDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.CategoriesQuickSummaryEntity
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryEntityV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryV2Dao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.EXPENSES_FK_CATEGORY_ID_NAME
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.EXPENSES_TABLE_NAME
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.ExpenseEntityV2
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.ExpenseV2Dao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice.SearchServiceDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.searchservice.SearchServiceEntity

// TODO: write tests for it
// maybe i should put schema to the app resources bia gradle??
val MIGRATION_1_2 =
    object : Migration(1, 2) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL("CREATE INDEX `index_expenses_fk_category_id` ON `$EXPENSES_TABLE_NAME` (`$EXPENSES_FK_CATEGORY_ID_NAME`)")
        }
    }

@Database(
    version = 2,
    entities = [
        CategoriesQuickSummaryEntity::class,
        CategoryEntityV2::class,
        ExpenseEntityV2::class,
        SearchServiceEntity::class,
    ],
)
@TypeConverters(
    value = [
        InstantConverter::class,
        BigDecimalDoubleTypeConverter::class,
    ],
)
abstract class AppDatabaseV2 : RoomDatabase() {
    abstract fun categoriesQuickSummaryDao(): CategoriesQuickSummaryDao
    abstract fun categoryV2Dao(): CategoryV2Dao
    abstract fun expenseV2Dao(): ExpenseV2Dao
    abstract fun searchServiceDao(): SearchServiceDao

    fun getSQLiteDB(): SupportSQLiteOpenHelper =
        openHelper
}

package com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.CategoriesQuickSummaryDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.categoriesquicksummary.CategoriesQuickSummaryEntity
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.BigDecimalDoubleTypeConverter
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.converters.InstantConverter
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.category.CategoryEntity
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.EXPENSES_FK_CATEGORY_ID_NAME
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.EXPENSES_TABLE_NAME
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.ExpenseDao
import com.mateuszcholyn.wallet.backend.impl.infrastructure.sqlite.core.expense.ExpenseEntity
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

val MIGRATION_2_3 =
    object : Migration(2, 3) {
        override fun migrate(db: SupportSQLiteDatabase) {
            db.execSQL(
                """
                DROP INDEX IF EXISTS `index_categories_name`;
                """.trimIndent()
            )
        }
    }

@Database(
    version = 3,
    entities = [
        CategoriesQuickSummaryEntity::class,
        CategoryEntity::class,
        ExpenseEntity::class,
        SearchServiceEntity::class,
    ],
)
@TypeConverters(
    value = [
        InstantConverter::class,
        BigDecimalDoubleTypeConverter::class,
    ],
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun categoriesQuickSummaryDao(): CategoriesQuickSummaryDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao
    abstract fun searchServiceDao(): SearchServiceDao

}

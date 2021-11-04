package com.mateuszcholyn.wallet.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mateuszcholyn.wallet.domain.expense.db.ExpenseDao
import com.mateuszcholyn.wallet.domain.expense.db.model.Expense
import com.mateuszcholyn.wallet.domain.moneysaver.db.MonthlyBudgetDao
import com.mateuszcholyn.wallet.domain.moneysaver.db.model.MonthlyBudget
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.CategoryEntity


@Database(
        entities = [
            MonthlyBudget::class,
            CategoryEntity::class,
            Expense::class],
        version = 2)
@TypeConverters(LocalDateTimeConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun monthlyBudgetDao(): MonthlyBudgetDao
    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) =
                instance
                        ?: synchronized(LOCK) {
                            instance
                                    ?: buildDatabase(context).also { instance = it }
                        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(
                        context,
                        AppDatabase::class.java, "database.db"
                )
                        //TODO this should be fixed!!
                        .allowMainThreadQueries()
                        .build()
    }

}

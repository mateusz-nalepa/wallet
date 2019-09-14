package com.mateuszcholyn.wallet.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.mateuszcholyn.wallet.domain.category.db.CategoryDao
import com.mateuszcholyn.wallet.domain.category.db.model.Category
import com.mateuszcholyn.wallet.domain.expense.db.ExpenseDao
import com.mateuszcholyn.wallet.domain.expense.db.model.Expense
import com.mateuszcholyn.wallet.domain.moneysaver.db.MonthlyBudgetDao
import com.mateuszcholyn.wallet.domain.moneysaver.db.model.MonthlyBudget


@Database(
        entities = [
            MonthlyBudget::class,
            Category::class,
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

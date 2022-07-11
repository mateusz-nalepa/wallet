package com.mateuszcholyn.wallet.di.hilt

import android.content.Context
import androidx.room.Room
import com.mateuszcholyn.wallet.config.AppDatabase
import com.mateuszcholyn.wallet.di.demomode.InMemoryCategoryRepository
import com.mateuszcholyn.wallet.di.demomode.InMemoryExpenseRepository
import com.mateuszcholyn.wallet.domain.DemoAppSwitcher
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.infrastructure.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.SqLiteExpenseRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltProdDatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "database.db",
        )
            .allowMainThreadQueries() // TODO this should be fixed!!
            .build()

    @Provides
    @Singleton
    fun provideCategoryRepository(
        appDatabase: AppDatabase,
        expenseRepository: ExpenseRepository,
        demoAppSwitcher: DemoAppSwitcher,
    ): CategoryRepository {
        if (demoAppSwitcher.isDemoModeEnabled()) {
            return InMemoryCategoryRepository(expenseRepository)
        }

        return SqLiteCategoryRepository(
            categoryDao = appDatabase.categoryDao(),
        )
    }

    @Provides
    @Singleton
    fun provideExpenseRepository(
        appDatabase: AppDatabase,
        demoAppSwitcher: DemoAppSwitcher,
    ): ExpenseRepository {
        if (demoAppSwitcher.isDemoModeEnabled()) {
            return InMemoryExpenseRepository()
        }
        return SqLiteExpenseRepository(
            expenseDao = appDatabase.expenseDao(),
        )
    }

}

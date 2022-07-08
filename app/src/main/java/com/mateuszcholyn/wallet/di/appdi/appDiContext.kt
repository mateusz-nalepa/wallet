package com.mateuszcholyn.wallet.di.appdi

import android.content.Context
import com.mateuszcholyn.wallet.config.AppDatabase
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseDao
import com.mateuszcholyn.wallet.infrastructure.expense.SqLiteExpenseRepository
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider


fun createDependencyContext(
        applicationContext: Context,
): DI {
    return DI {
        val appDatabase = AppDatabase(applicationContext)

        //Category
        bind<CategoryDao>() with provider { appDatabase.categoryDao() }
        bind<CategoryRepository>() with provider { SqLiteCategoryRepository(appDatabase.categoryDao()) }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseDao>() with provider { appDatabase.expenseDao() }
        bind<ExpenseRepository>() with provider { SqLiteExpenseRepository(instance()) }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

    }

}

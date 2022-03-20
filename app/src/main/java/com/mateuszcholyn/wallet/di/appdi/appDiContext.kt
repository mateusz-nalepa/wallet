package com.mateuszcholyn.wallet.di.appdi

import android.app.Activity
import android.content.Context
import com.mateuszcholyn.wallet.config.AppDatabase
import com.mateuszcholyn.wallet.di.ActivityProvider
import com.mateuszcholyn.wallet.domain.DemoAppEnabledProvider
import com.mateuszcholyn.wallet.domain.DemoModeDisabled
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
        activity: Activity,
        applicationContext: Context,
): DI {
    return DI {
        val appDatabase = AppDatabase(applicationContext)

        //Demo Mode
        bind<DemoAppEnabledProvider>() with provider { DemoModeDisabled }

        //Category
        bind<CategoryDao>() with provider { appDatabase.categoryDao() }
        bind<CategoryRepository>() with provider { SqLiteCategoryRepository(appDatabase.categoryDao()) }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseDao>() with provider { appDatabase.expenseDao() }
        bind<ExpenseRepository>() with provider { SqLiteExpenseRepository(instance()) }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

        //
        bind<ActivityProvider>() with provider { ActivityProvider(activity) }
    }

}

package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.mateuszcholyn.wallet.database.AppDatabase
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.domain.moneysaver.db.MonthlyBudgetDao
import com.mateuszcholyn.wallet.domain.moneysaver.service.MoneySaverService
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseDao
import com.mateuszcholyn.wallet.infrastructure.expense.SqLiteExpenseRepository
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler
import net.danlew.android.joda.JodaTimeAndroid


class ApplicationContext : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        JodaTimeAndroid.init(this)
        AppDatabase(appContext)
        GlobalExceptionHandler(this)
    }

    override val kodein by Kodein.lazy {
        val appDatabase = AppDatabase(appContext)

        //Category
        bind<CategoryDao>() with provider { appDatabase.categoryDao() }
        bind<CategoryRepository>() with provider { SqLiteCategoryRepository(appDatabase.categoryDao()) }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseDao>() with provider { appDatabase.expenseDao() }
        bind<ExpenseRepository>() with provider { SqLiteExpenseRepository(instance()) }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

        //MoneySaver
        bind<MonthlyBudgetDao>() with provider { appDatabase.monthlyBudgetDao() }
        bind<MoneySaverService>() with provider { MoneySaverService(instance(), instance()) }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}


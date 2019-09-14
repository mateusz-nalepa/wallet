package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.mateuszcholyn.wallet.database.AppDatabase
import com.mateuszcholyn.wallet.domain.category.db.CategoryDao
import com.mateuszcholyn.wallet.domain.category.service.CategoryService
import com.mateuszcholyn.wallet.domain.expense.db.ExpenseDao
import com.mateuszcholyn.wallet.domain.expense.service.ExpenseService
import com.mateuszcholyn.wallet.domain.moneysaver.service.MoneySaverService
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
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseDao>() with provider { appDatabase.expenseDao() }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

        //MoneySaver
        bind<MoneySaverService>() with provider { MoneySaverService() }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}


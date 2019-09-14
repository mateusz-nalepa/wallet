package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.mateuszcholyn.wallet.database.AppDatabase
import com.mateuszcholyn.wallet.database.DatabaseHelper
import com.mateuszcholyn.wallet.domain.category.db.CategoryExecutor
import com.mateuszcholyn.wallet.domain.category.service.CategoryService
import com.mateuszcholyn.wallet.domain.expense.db.ExpenseExecutor
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
        val dbHelper = DatabaseHelper(appContext)

        //Category
        bind<CategoryExecutor>() with provider { CategoryExecutor(appContext) }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseExecutor>() with provider { ExpenseExecutor(appContext, instance()) }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

        //MoneySaver
        bind<MoneySaverService>() with provider { MoneySaverService() }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}


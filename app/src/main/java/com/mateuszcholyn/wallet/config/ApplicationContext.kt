package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.mateuszcholyn.wallet.category.db.CategoryExecutor
import com.mateuszcholyn.wallet.category.service.Categoryservice
import com.mateuszcholyn.wallet.expense.db.ExpenseExecutor
import com.mateuszcholyn.wallet.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler

class ApplicationContext : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        GlobalExceptionHandler(this)
    }

    override val kodein by Kodein.lazy {
        //Expense
        bind<ExpenseExecutor>() with provider { ExpenseExecutor(appContext) }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

        //Category
        bind<CategoryExecutor>() with provider { CategoryExecutor(appContext) }
        bind<Categoryservice>() with provider { Categoryservice(instance()) }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}


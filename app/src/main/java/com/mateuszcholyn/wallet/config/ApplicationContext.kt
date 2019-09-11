package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import com.github.salomonbrys.kodein.*
import com.mateuszcholyn.wallet.domain.category.db.CategoryExecutor
import com.mateuszcholyn.wallet.domain.category.service.CategoryService
import com.mateuszcholyn.wallet.domain.expense.db.ExpenseExecutor
import com.mateuszcholyn.wallet.domain.expense.service.ExpenseService
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler


class ApplicationContext : Application(), KodeinAware {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        GlobalExceptionHandler(this)
    }

    override val kodein by Kodein.lazy {
        //Category
        bind<CategoryExecutor>() with provider { CategoryExecutor(appContext) }
        bind<CategoryService>() with provider { CategoryService(instance()) }

        //Expense
        bind<ExpenseExecutor>() with provider { ExpenseExecutor(appContext, instance()) }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}


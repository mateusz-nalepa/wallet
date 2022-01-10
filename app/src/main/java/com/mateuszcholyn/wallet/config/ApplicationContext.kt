package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.domain.moneysaver.MoneySaverService
import com.mateuszcholyn.wallet.domain.moneysaver.MonthlyBudgetRepository
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseDao
import com.mateuszcholyn.wallet.infrastructure.expense.SqLiteExpenseRepository
import com.mateuszcholyn.wallet.infrastructure.moneysaver.MonthlyBudgetDao
import com.mateuszcholyn.wallet.infrastructure.moneysaver.SqLiteMonthlyBudgetRepository
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler
import org.kodein.di.*


class ApplicationContext : Application(), DIAware {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AppDatabase(appContext)
        GlobalExceptionHandler(this)
        appDi = di
    }

    override val di by DI.lazy {
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
        bind<MonthlyBudgetRepository>() with provider { SqLiteMonthlyBudgetRepository(instance()) }
        bind<MoneySaverService>() with provider { MoneySaverService(instance(), instance()) }
    }

    companion object {
        lateinit var appContext: Context
            private set

        lateinit var appDi: DI
            private set
    }
}


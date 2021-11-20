package com.mateuszcholyn.wallet.config

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.mateuszcholyn.wallet.CustomViewModelFactory
import com.mateuszcholyn.wallet.bindViewModel
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
import com.mateuszcholyn.wallet.ui.addexpense.SummaryViewModel
import com.mateuszcholyn.wallet.ui.chat.ChatViewModel
import com.mateuszcholyn.wallet.util.GlobalExceptionHandler
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider
import org.kodein.di.singleton


class ApplicationContext : Application(), DIAware {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AppDatabase(appContext)
        GlobalExceptionHandler(this)
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

//        ViewModelFactory
        bindViewModel<SummaryViewModel>() with provider { SummaryViewModel() }
        bindViewModel<ChatViewModel>() with provider { ChatViewModel() }
//        bindViewModel<SummaryViewModel>() with provider { SummaryViewModel(instance()) }


        //        ViewModelFactory
//        bindViewModel<SummaryViewModel>() with provider { SummaryViewModel(instance()) }
//        bind<ViewModelProvider.Factory>() with singleton { CustomViewModelFactory(directDI) }

        bind<ViewModelProvider.Factory>() with singleton { CustomViewModelFactory(directDI) }
    }

    companion object {
        lateinit var appContext: Context
            private set
    }
}


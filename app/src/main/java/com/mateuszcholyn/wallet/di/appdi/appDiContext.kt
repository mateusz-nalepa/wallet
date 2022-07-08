package com.mateuszcholyn.wallet.di.appdi

import android.content.Context
import com.mateuszcholyn.wallet.di.demodi.InMemoryCategoryRepository
import com.mateuszcholyn.wallet.di.demodi.InMemoryExpenseRepository
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.category.CategoryService
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.provider


fun createDependencyContext(
        applicationContext: Context,
): DI {
    return DI {
        //Expense
//        bind<ExpenseDao>() with provider { appDatabase.expenseDao() }

        val inMemoryExpenseRepository = InMemoryExpenseRepository()

        bind<ExpenseRepository>() with provider { inMemoryExpenseRepository }
        bind<ExpenseService>() with provider { ExpenseService(instance()) }

        //Category
//        bind<CategoryDao>() with provider { appDatabase.categoryDao() }
        bind<CategoryRepository>() with provider { InMemoryCategoryRepository(inMemoryExpenseRepository) }
        bind<CategoryService>() with provider { CategoryService(instance()) }
    }

}

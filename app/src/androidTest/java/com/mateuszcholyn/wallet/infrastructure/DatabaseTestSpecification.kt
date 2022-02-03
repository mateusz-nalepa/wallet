package com.mateuszcholyn.wallet.infrastructure

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mateuszcholyn.wallet.config.AppDatabase
import com.mateuszcholyn.wallet.domain.category.CategoryRepository
import com.mateuszcholyn.wallet.domain.expense.ExpenseRepository
import com.mateuszcholyn.wallet.infrastructure.category.CategoryDao
import com.mateuszcholyn.wallet.infrastructure.category.SqLiteCategoryRepository
import com.mateuszcholyn.wallet.infrastructure.expense.ExpenseDao
import com.mateuszcholyn.wallet.infrastructure.expense.SqLiteExpenseRepository
import org.junit.After
import org.junit.Before
import java.io.IOException

internal open class DatabaseTestSpecification {

    lateinit var categoryRepository: CategoryRepository
    lateinit var categoryDao: CategoryDao

    lateinit var expenseRepository: ExpenseRepository
    lateinit var expenseDao: ExpenseDao

    lateinit var db: AppDatabase

    @Before
    fun before() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()

        categoryDao = db.categoryDao()
        categoryRepository = SqLiteCategoryRepository(categoryDao)

        expenseDao = db.expenseDao()
        expenseRepository = SqLiteExpenseRepository(expenseDao)
    }

    @After
    @Throws(IOException::class)
    fun after() {
        db.close()
    }

}
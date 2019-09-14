package com.mateuszcholyn.wallet.domain.expense.db

import android.support.test.runner.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.database.dao.CategoryDao
import com.mateuszcholyn.wallet.database.dao.ExpenseDao
import com.mateuszcholyn.wallet.database.model.Category
import com.mateuszcholyn.wallet.database.model.Expense
import org.joda.time.LocalDateTime
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ExpenseDaoTest : DatabaseTestSpecification() {

    private lateinit var expenseDao: ExpenseDao
    private lateinit var categoryDao: CategoryDao

    @Before
    fun setUp() {
        expenseDao = db.expenseDao()
        categoryDao = db.categoryDao()
        addInitCategory()
    }

    private fun addInitCategory() {
        categoryDao.insert(Category(name = "Basic"))
    }

    @Test
    fun shouldAddExpense() {
        //given
        val expense = Expense(amount = 50.0, categoryId = 1L, date = LocalDateTime(), description = "Hodorek")

        //when
        val savedExpense = expenseDao.insert(expense)

        //then
        savedExpense == 1L
    }

}

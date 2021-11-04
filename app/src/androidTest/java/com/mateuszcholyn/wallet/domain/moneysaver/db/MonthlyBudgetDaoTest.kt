package com.mateuszcholyn.wallet.domain.moneysaver.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.domain.moneysaver.db.model.MonthlyBudget
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MonthlyBudgetDaoTest : DatabaseTestSpecification() {

    private lateinit var monthlyBudgetDao: MonthlyBudgetDao

    @Before
    fun setUp() {
        monthlyBudgetDao = db.monthlyBudgetDao()
    }

    @Test
    fun shouldFetchAllMonthlyBudgets() {
        //given
        val all = monthlyBudgetDao.getAll()

        //expect
        assertEquals(true, all.isEmpty())
    }

    @Test
    fun shouldAddMonthlyBudget() {
        //given
        val budget = MonthlyBudget(budget = 300.0, year = 2019, month = 9)

        //when
        val savedBudgetId = monthlyBudgetDao.insert(budget)

        //then
        assertEquals(1L, savedBudgetId)
    }

    @Test
    fun shouldGetMonthlyBudget() {
        //given
        val budget = MonthlyBudget(budget = 300.0, year = 2019, month = 9)
        monthlyBudgetDao.insert(budget)

        //when
        val savedBudget = monthlyBudgetDao.get(2019, 9)!!

        //then
        assertEquals(300.0, savedBudget.budget)
    }

}

package com.mateuszcholyn.wallet.domain.moneysaver.db

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.infrastructure.moneysaver.MonthlyBudgetDao
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class MonthlyBudgetEntityDaoTest : DatabaseTestSpecification() {

    private lateinit var monthlyBudgetDao: MonthlyBudgetDao

    @Before
    fun setUp() {
        monthlyBudgetDao = db.monthlyBudgetDao()
    }

//    @Test
//    fun shouldFetchAllMonthlyBudgets() {
//        //given
//        val all = monthlyBudgetDao.getAll()
//
//        //expect
//        assertEquals(true, all.isEmpty())
//    }

//    @Test
//    fun shouldAddMonthlyBudget() {
//        //given
//        val budget = MonthlyBudgetEntity(budget = 300.0, year = 2019, month = 9)
//
//        //when
//        val savedBudgetId = monthlyBudgetDao.insert(budget)
//
//        //then
//        assertEquals(1L, savedBudgetId)
//    }

}
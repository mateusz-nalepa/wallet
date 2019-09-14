package com.mateuszcholyn.wallet.domain.moneysaver.db

import android.support.test.runner.AndroidJUnit4
import com.mateuszcholyn.wallet.database.DatabaseTestSpecification
import com.mateuszcholyn.wallet.domain.moneysaver.db.model.MonthlyBudget
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
        all.isEmpty()
    }

    @Test
    fun shouldAddMonthlyBudget() {
        //given
        val budget = MonthlyBudget(budget = 300.0, year = 2019, month = 9)

        //when
        val savedBudget = monthlyBudgetDao.insert(budget)

        //then
        savedBudget == 1L
    }

}

package com.mateuszcholyn.wallet.infrastructure.moneysaver

import com.mateuszcholyn.wallet.domain.moneysaver.MonthlyBudget
import com.mateuszcholyn.wallet.domain.moneysaver.MonthlyBudgetRepository

class SqLiteMonthlyBudgetRepository(
    private val monthlyBudgetDao: MonthlyBudgetDao,
) : MonthlyBudgetRepository {
    override fun get(year: Int, month: Int): MonthlyBudget? {
        return monthlyBudgetDao.get(year, month)
            .let { it?.toDomain() }
    }
}


fun MonthlyBudgetEntity.toDomain(): MonthlyBudget {
    return MonthlyBudget(
        budget = budget,
    )
}
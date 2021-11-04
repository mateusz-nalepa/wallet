package com.mateuszcholyn.wallet.domain.moneysaver.service

import com.mateuszcholyn.wallet.domain.expense.ExpenseService
import com.mateuszcholyn.wallet.domain.moneysaver.db.MonthlyBudgetDao
import com.mateuszcholyn.wallet.domain.moneysaver.dto.MonthlyBudgetSummaryDto

class MoneySaverService(
    private val monthlyBudgetDao: MonthlyBudgetDao,
    private val expenseService: ExpenseService
) {

    fun monthlyBudgetSummaryFor(year: Int, month: Int): MonthlyBudgetSummaryDto {
        val budget = monthlyBudgetDao.get(year, month)?.budget ?: 0.0
        val spentMoney = expenseService.moneySpentIn(year, month)
        val savedMoney = budget - spentMoney
        return MonthlyBudgetSummaryDto(
            monthlyBudget = budget,
            spentMoney = spentMoney,
            savedMoney = savedMoney
        )
    }

}
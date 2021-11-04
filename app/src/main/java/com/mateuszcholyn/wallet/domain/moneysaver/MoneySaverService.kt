package com.mateuszcholyn.wallet.domain.moneysaver

import com.mateuszcholyn.wallet.domain.expense.ExpenseService

class MoneySaverService(
    private val expenseService: ExpenseService,
    private val monthlyBudgetRepository: MonthlyBudgetRepository
) {

    fun monthlyBudgetSummaryFor(year: Int, month: Int): MonthlyBudgetSummaryDto {
        val budget = monthlyBudgetRepository.get(year, month)?.budget ?: 0.0
        val spentMoney = expenseService.moneySpentIn(year, month)
        val savedMoney = budget - spentMoney
        return MonthlyBudgetSummaryDto(
            monthlyBudget = budget,
            spentMoney = spentMoney,
            savedMoney = savedMoney
        )
    }

}

data class MonthlyBudget(
    var budget: Double?,
)
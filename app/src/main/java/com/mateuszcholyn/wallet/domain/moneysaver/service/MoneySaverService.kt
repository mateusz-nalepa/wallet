package com.mateuszcholyn.wallet.domain.moneysaver.service

import com.mateuszcholyn.wallet.domain.moneysaver.dto.MonthlyBudgetSummaryDto

class MoneySaverService() {

    fun monthlyBudgetSummary() =
            MonthlyBudgetSummaryDto(0.0, 0.0, 0.0)

}
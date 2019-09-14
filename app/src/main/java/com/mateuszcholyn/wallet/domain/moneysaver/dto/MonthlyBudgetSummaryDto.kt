package com.mateuszcholyn.wallet.domain.moneysaver.dto

import java.io.Serializable

data class MonthlyBudgetSummaryDto(
        var monthlyBudget: Double = 0.0,
        var spentMoney: Double = 0.0,
        var savedMoney: Double = 0.0
) : Serializable

package com.mateuszcholyn.wallet.domain.moneysaver

interface MonthlyBudgetRepository {

    fun get(year: Int, month: Int): MonthlyBudget?

}
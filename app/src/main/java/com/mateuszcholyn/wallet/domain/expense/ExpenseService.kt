package com.mateuszcholyn.wallet.domain.expense

import java.time.LocalDateTime
import java.time.temporal.TemporalAdjusters.lastDayOfMonth


class ExpenseService(
    private val expenseRepository: ExpenseRepository,
) {

    fun addExpense(expense: Expense): Expense {
        return expenseRepository.add(expense)
    }

    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return expenseRepository.getAll(expenseSearchCriteria)
    }

    fun averageExpense(expenseSearchCriteria: ExpenseSearchCriteria): Double {
        return expenseRepository.averageAmount(expenseSearchCriteria)
    }

    fun hardRemove(expenseId: Long): Boolean =
        expenseRepository.remove(expenseId)

    fun updateExpense(expense: Expense): Expense {
        return expenseRepository.update(expense)
    }

    fun moneySpentIn(year: Int, month: Int): Double {
        val startRange = LocalDateTime.of(year, month, 1, 1, 1, 1)
        val maximumDayForGivenMonth = startRange.with(lastDayOfMonth()).dayOfMonth
        val endRange = LocalDateTime.of(year, month, maximumDayForGivenMonth, 23, 59, 59)
        return expenseRepository.moneySpentBetween(startRange, endRange)
    }

}
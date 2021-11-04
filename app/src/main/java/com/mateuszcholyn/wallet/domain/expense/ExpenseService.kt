package com.mateuszcholyn.wallet.domain.expense

import org.joda.time.LocalDateTime

class ExpenseService(
    private val expenseRepository: ExpenseRepository,
) {

    fun addExpense(expense: Expense): Expense {
        return expenseRepository.add(expense)
    }

    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return expenseRepository.getAll(expenseSearchCriteria)
    }

    fun averageExpense(averageSearchCriteria: AverageSearchCriteria): Double {
        return expenseRepository.averageAmount(averageSearchCriteria)
    }

    fun hardRemove(expenseId: Long): Boolean =
        expenseRepository.remove(expenseId)

    fun updateExpense(expense: Expense): Expense {
        return expenseRepository.update(expense)
    }

    fun moneySpentIn(year: Int, month: Int): Double {
        val startRange = LocalDateTime(year, month, 1, 1, 1, 1)
        val maximumDayForGivenMonth = startRange.dayOfMonth().maximumValue
        val endRange = LocalDateTime(year, month, maximumDayForGivenMonth, 23, 59, 59)
        return expenseRepository.moneySpentBetween(startRange, endRange)
    }

}
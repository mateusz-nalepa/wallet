package com.mateuszcholyn.wallet.domain.expense

import java.time.Duration


class ExpenseService(
        private val expenseRepository: ExpenseRepository,
) {

    fun getById(expenseId: Long): Expense {
        return expenseRepository.getById(expenseId)
    }

    fun addExpense(expense: Expense): Expense {
        return expenseRepository.add(expense)
    }

    fun getAll(expenseSearchCriteria: ExpenseSearchCriteria): List<Expense> {
        return expenseRepository.getAll(expenseSearchCriteria)
    }

    fun averageExpense(expenseSearchCriteria: ExpenseSearchCriteria): AverageExpenseResult {

        var days =
                Duration.between(expenseSearchCriteria.beginDate, expenseSearchCriteria.endDate)
                        .toDays()

        if (days == 0L) {
            days += 1 // have no idea why XD
        }

        val all = expenseRepository.getAll(expenseSearchCriteria)

        val sum = all.map { it.amount }.sum()


        return AverageExpenseResult(
                wholeAmount = sum,
                days = days.toInt(),
                averageAmount = sum / days
        )
    }

    fun hardRemove(expenseId: Long): Boolean =
            expenseRepository.remove(expenseId)

    fun updateExpense(expense: Expense): Expense {
        return expenseRepository.update(expense)
    }

}

data class AverageExpenseResult(
        val wholeAmount: Double,
        val days: Int,
        val averageAmount: Double,
)
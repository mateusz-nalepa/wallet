package com.mateuszcholyn.wallet.domain.expense

import java.time.Duration


class ExpenseService(
        private val expenseRepository: ExpenseRepository,
) {

    fun getById(expenseId: Long): Expense {
        return expenseRepository.getById(expenseId)
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

        val sum = all.sumExpensesAmount()


        return AverageExpenseResult(
                wholeAmount = sum,
                days = days.toInt(),
                averageAmount = sum / days
        )
    }

    fun hardRemove(expenseId: Long): Boolean =
            expenseRepository.remove(expenseId)

    fun saveExpense(expense: Expense): Expense =
            if (expense.id != -1L) {
                updateExpense(expense)
            } else {
                addExpense(expense)
            }

    private fun updateExpense(expense: Expense): Expense {
        return expenseRepository.update(expense)
    }

    private fun addExpense(expense: Expense): Expense {
        return expenseRepository.add(expense)
    }

}

data class AverageExpenseResult(
        val wholeAmount: Double,
        val days: Int,
        val averageAmount: Double,
)

fun List<Expense>.sumExpensesAmount(): Double =
        this.map { it.amount }.sum()
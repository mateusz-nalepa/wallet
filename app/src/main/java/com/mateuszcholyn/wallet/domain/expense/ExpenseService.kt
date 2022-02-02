package com.mateuszcholyn.wallet.domain.expense

import com.mateuszcholyn.wallet.scaffold.screens.isEditable
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration

data class SummaryResult(
        val expenses: List<Expense>,
        val averageExpenseResult: AverageExpenseResult,
)

class ExpenseService(
        private val expenseRepository: ExpenseRepository,
) {

    fun getById(expenseId: Long): Expense {
        return expenseRepository.getById(expenseId)
    }

    fun getSummary(expenseSearchCriteria: ExpenseSearchCriteria): SummaryResult {
        val expenses = expenseRepository.getAll(expenseSearchCriteria)

        return SummaryResult(
                expenses = expenses,
                averageExpenseResult = averageExpense(expenses, expenseSearchCriteria),
        )
    }

    private fun averageExpense(
            expenses: List<Expense>,
            expenseSearchCriteria: ExpenseSearchCriteria,
    ): AverageExpenseResult {
        val sum = expenses.sumExpensesAmount()

        var days =
                Duration.between(expenseSearchCriteria.beginDate, expenseSearchCriteria.endDate)
                        .toDays()

        if (days == 0L) {
            days += 1 // have no idea why XD
        }

        return AverageExpenseResult(
                wholeAmount = sum,
                days = days.toInt(),
                averageAmount = sum.divide(days.toBigDecimal(), 2, RoundingMode.HALF_UP)
        )
    }

    fun hardRemove(expenseId: Long): Boolean =
            expenseRepository.remove(expenseId)

    fun saveExpense(expense: Expense): Expense =
            if (expense.id.isEditable()) {
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
        val wholeAmount: BigDecimal,
        val days: Int,
        val averageAmount: BigDecimal,
)

fun List<Expense>.sumExpensesAmount(): BigDecimal =
        this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
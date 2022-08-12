package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration

object SearchAverageExpenseResultCalculator {

    fun calculate(
        expenses: List<SearchSingleResult>,
        searchCriteria: SearchCriteria,
    ): SearchAverageExpenseResult {
        val sum = expenses.sumExpensesAmount()
        val days = resolveNumberOfDays(expenses, searchCriteria)

        return SearchAverageExpenseResult(
            wholeAmount = sum,
            days = resolveNumberOfDays(expenses, searchCriteria),
            averageAmount = sum.divide(days.toBigDecimal(), 2, RoundingMode.HALF_UP)
        )
    }

    private fun List<SearchSingleResult>.sumExpensesAmount(): BigDecimal =
        if (this.isNotEmpty()) {
            this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
        } else {
            BigDecimal.ZERO
        }

    private fun resolveNumberOfDays(
        expenses: List<SearchSingleResult>,
        searchCriteria: SearchCriteria,
    ): Int {
        var days =
            if (expenses.isEmpty()) {
                0
            } else {
                val minimum = searchCriteria.beginDate ?: expenses.minOf { it.paidAt }
                val maximum = searchCriteria.endDate ?: expenses.maxOf { it.paidAt }

                Duration
                    .between(minimum, maximum)
                    .toDays()
            }

        if (days == 0L) {
            days += 1
        }

        return days.toInt()
    }

}
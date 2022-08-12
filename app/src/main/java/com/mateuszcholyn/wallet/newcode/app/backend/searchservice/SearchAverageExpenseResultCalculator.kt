package com.mateuszcholyn.wallet.newcode.app.backend.searchservice

import com.mateuszcholyn.wallet.util.dateutils.maxDate
import com.mateuszcholyn.wallet.util.dateutils.minDate
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration

object SearchAverageExpenseResultCalculator {

    fun calculate(
        expenses: List<SearchSingleResult>,
        searchCriteria: SearchCriteria,
    ): SearchAverageExpenseResult {
        val sum = expenses.sumExpensesAmount()

        var days =
            if (expenses.isEmpty()) {
                0
            } else {
                // HODOR: fix me, remove values if not neccessary
                val minimum =
                    if (searchCriteria.beginDate != null && searchCriteria.beginDate != minDate ) searchCriteria.beginDate else expenses.minOf { it.paidAt }
                val maximum =
                    if (searchCriteria.endDate != null && searchCriteria.endDate != maxDate ) searchCriteria.endDate else expenses.maxOf { it.paidAt }

                Duration
                    .between(minimum, maximum)
                    .toDays()
            }

        if (days == 0L) {
            days += 1
        }

        return SearchAverageExpenseResult(
            wholeAmount = sum,
            days = days.toInt(),
            averageAmount = sum.divide(days.toBigDecimal(), 2, RoundingMode.HALF_UP)
        )
    }

    private fun List<SearchSingleResult>.sumExpensesAmount(): BigDecimal =
        if (this.isNotEmpty()) {
            this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
        } else {
            BigDecimal.ZERO
        }

}
package com.mateuszcholyn.wallet.backend.impl.domain.searchservice

import com.mateuszcholyn.wallet.backend.api.searchservice.SearchAverageExpenseResult
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchCriteria
import com.mateuszcholyn.wallet.backend.api.searchservice.SearchSingleResult
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atStartOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.plusIntDays
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
                var minimum = searchCriteria.beginDate ?: expenses.minOf { it.paidAt }
                var maximum = searchCriteria.endDate ?: expenses.maxOf { it.paidAt }

                // Show All Expenses up to today
                if (searchCriteria.beginDate == null) {
                    maximum = maximum.plusIntDays(1)
                }

                Duration
                    /**
                     * Write test for this case: atStartOfTheDay, atEndOfTheDay
                     */
                    .between(minimum.atStartOfTheDay(), maximum.atEndOfTheDay())
                    .toDays()
            }

        if (days == 0L) {
            days += 1
        }

        return days.toInt()
    }

}
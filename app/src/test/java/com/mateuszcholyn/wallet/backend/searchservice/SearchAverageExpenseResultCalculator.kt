package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.core.CategoryId
import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDateTime

data class SearchCriteria(
    val allCategories: Boolean? = null,
    val categoryId: CategoryId? = null,
    val beginDate: LocalDateTime? = null,
    val endDate: LocalDateTime? = null,
)


object SearchAverageExpenseResultCalculator {

    fun calculate(
        expenses: List<ExpenseAddedEvent>,
        searchCriteria: SearchCriteria,
    ): SearchAverageExpenseResult {
        val sum = expenses.sumExpensesAmount()

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

        return SearchAverageExpenseResult(
            wholeAmount = sum,
            days = days.toInt(),
            averageAmount = sum.divide(days.toBigDecimal(), 2, RoundingMode.HALF_UP)
        )
    }

    private fun List<ExpenseAddedEvent>.sumExpensesAmount(): BigDecimal =
        if (this.isNotEmpty()) {
            this.map { it.amount }.reduce { acc, bigDecimal -> acc.add(bigDecimal) }
        } else {
            BigDecimal.ZERO
        }

}
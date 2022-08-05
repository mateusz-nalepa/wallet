package com.mateuszcholyn.wallet.backend.searchservice

import com.mateuszcholyn.wallet.backend.events.ExpenseAddedEvent
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class SearchCriteria(
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime,
)

private fun defaultSearchCriteria(): SearchCriteria =
    SearchCriteria(
        beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
        endDate = LocalDateTime.now().plusHours(1),
    )


object SearchAverageExpenseResultCalculator {

    fun calculate(
        expenses: List<ExpenseAddedEvent>,
        searchCriteria: SearchCriteria = defaultSearchCriteria(),
    ): SearchAverageExpenseResult {
        val sum = expenses.sumExpensesAmount()


//        var days =
//            if (expenseSearchCriteria.isAllExpenses) {
//                if (expenses.isEmpty()) {
//                    0
//                } else {
//                    val minimum = expenses.minOfOrNull { it.date }
//                    val maximum = LocalDateTime.now()
//
//                    Duration.between(minimum, maximum)
//                        .toDays()
//                }
//
//
//            } else {
//                expenseSearchCriteria.toNumberOfDays()
//            }

//        val firstExpense = expenses.first { expenses. }

        var days = searchCriteria.toNumberOfDays()

        if (days == 0L) {
            days += 1 // have no idea why XD
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

    private fun SearchCriteria.toNumberOfDays(): Long =
        Duration
            .between(this.beginDate, this.endDate)
            .toDays()

}
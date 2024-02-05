package com.mateuszcholyn.wallet.frontend.view.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atStartOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atStartOfTheMonth
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class QuickRangeData(
    override val name: String,
    override val nameKey: Int? = null,
    val beginDate: LocalDateTime?,
    val endDate: LocalDateTime?,
) : DropdownElement {
    companion object {
        val default =
            QuickRangeData(
                name = "R.string.today",
                nameKey = R.string.today,
                beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                endDate = LocalDateTime.now().plusHours(1),
            )
    }
}

fun quickDateRanges(): List<QuickRangeData> {
    return listOf(
        QuickRangeData.default,
        QuickRangeData(
            name = "R.string.yesterday",
            nameKey = R.string.yesterday,
            beginDate = LocalDateTime.now().minusDays(1).atStartOfTheDay(),
            endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX),
        ),
        QuickRangeData(
            name = "R.string.dayBeforeYesterday",
            nameKey = R.string.dayBeforeYesterday,
            beginDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN),
            endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX),
        ),
        QuickRangeData(
            name = "R.string.lastWeek",
            nameKey = R.string.lastWeek,
            beginDate = LocalDateTime.now().minusDays(7),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            name = "R.string.thisMonth",
            nameKey = R.string.thisMonth,
            beginDate = LocalDateTime.now().atStartOfTheMonth(),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            name = "R.string.lastMonth",
            nameKey = R.string.lastMonth,
            beginDate = LocalDateTime.now().minusMonths(1),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            name = "R.string.lastThreeMonths",
            nameKey = R.string.lastThreeMonths,
            beginDate = LocalDateTime.now().minusMonths(3),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            name = "R.string.allExpenses",
            nameKey = R.string.allExpenses,
            beginDate = null,
            endDate = null,
        ),
        QuickRangeData(
            name = "R.string.allExpensesUpToToday",
            nameKey = R.string.allExpensesUpToToday,
            beginDate = null,
            endDate = LocalDateTime.now().atEndOfTheDay(),
        )
    )

}

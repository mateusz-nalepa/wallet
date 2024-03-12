package com.mateuszcholyn.wallet.frontend.view.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atEndOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atStartOfTheDay
import com.mateuszcholyn.wallet.util.localDateTimeUtils.atStartOfTheMonth
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

data class QuickRangeData(
    override val name: String? = null,
    override val subName: String? = null,
    override val nameKey: Int,
    val beginDate: LocalDateTime?,
    val endDate: LocalDateTime?,
    val isCustomRangeData: Boolean = false,
) : DropdownElement {

    companion object {
        val default =
            QuickRangeData(
                nameKey = R.string.quickRange_today,
                beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                endDate = LocalDateTime.of(LocalDate.now(), LocalTime.MAX),
            )
    }
}

fun quickDateRanges(): List<QuickRangeData> {
    return listOf(
        QuickRangeData.default,
        QuickRangeData(
            nameKey = R.string.quickRange_yesterday,
            beginDate = LocalDateTime.now().minusDays(1).atStartOfTheDay(),
            endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_dayBeforeYesterday,
            beginDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN),
            endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_lastWeek,
            beginDate = LocalDateTime.now().minusDays(7),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_thisMonth,
            beginDate = LocalDateTime.now().atStartOfTheMonth(),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_lastMonth,
            beginDate = LocalDateTime.now().minusMonths(1),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_lastThreeMonths,
            beginDate = LocalDateTime.now().minusMonths(3),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_allExpensesToThisDay,
            beginDate = null,
            endDate = LocalDateTime.now().atEndOfTheDay(),
        ),
        QuickRangeData(
            nameKey = R.string.quickRange_allExpenses,
            beginDate = null,
            endDate = null,
        ),
        // TODO: weź to na samą górę XD
        QuickRangeData(
            nameKey = R.string.quickRange_custom,
            beginDate = null,
            endDate = null,
            isCustomRangeData = true,
        ),
    )

}

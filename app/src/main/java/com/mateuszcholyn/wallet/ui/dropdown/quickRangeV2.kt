package com.mateuszcholyn.wallet.ui.dropdown

import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.dateutils.atStartOfTheDay
import com.mateuszcholyn.wallet.util.dateutils.atStartOfTheMonth
import com.mateuszcholyn.wallet.util.dateutils.maxDate
import com.mateuszcholyn.wallet.util.dateutils.minDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class QuickRangeDataV2(
    override val name: String,
    override val nameKey: Int? = null,
    val beginDate: LocalDateTime,
    val endDate: LocalDateTime,
    val isAllExpenses: Boolean = false,
) : DropdownElement

fun quickRanges(): List<QuickRangeDataV2> {
    return listOf(
        QuickRangeDataV2(
            name = "R.string.today",
            nameKey = R.string.today,
            beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
            endDate = LocalDateTime.now().plusHours(1),
        ),
        QuickRangeDataV2(
            name = "R.string.yesterday",
            nameKey = R.string.yesterday,
            beginDate = LocalDateTime.now().minusDays(1).atStartOfTheDay(),
            endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX),
        ),
        QuickRangeDataV2(
            name = "R.string.dayBeforeYesterday",
            nameKey = R.string.dayBeforeYesterday,
            beginDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN),
            endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX),
        ),
        QuickRangeDataV2(
            name = "R.string.lastWeek",
            nameKey = R.string.lastWeek,
            beginDate = LocalDateTime.now().minusDays(7),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeDataV2(
            name = "R.string.thisMonth",
            nameKey = R.string.thisMonth,
            beginDate = LocalDateTime.now().atStartOfTheMonth(),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeDataV2(
            name = "R.string.lastMonth",
            nameKey = R.string.lastMonth,
            beginDate = LocalDateTime.now().minusMonths(1),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeDataV2(
            name = "R.string.lastThreeMonths",
            nameKey = R.string.lastThreeMonths,
            beginDate = LocalDateTime.now().minusMonths(3),
            endDate = LocalDateTime.now(),
        ),
        QuickRangeDataV2(
            name = "R.string.allExpenses",
            nameKey = R.string.allExpenses,
            beginDate = minDate,
            endDate = maxDate,
            isAllExpenses = true,
        )
    )

}

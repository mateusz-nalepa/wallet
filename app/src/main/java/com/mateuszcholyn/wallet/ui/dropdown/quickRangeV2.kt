package com.mateuszcholyn.wallet.ui.dropdown

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mateuszcholyn.wallet.R
import com.mateuszcholyn.wallet.util.atStartOfTheDay
import com.mateuszcholyn.wallet.util.atStartOfTheMonth
import com.mateuszcholyn.wallet.util.maxDate
import com.mateuszcholyn.wallet.util.minDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class QuickRangeDataV2(
        override val name: String,
        val beginDate: LocalDateTime,
        val endDate: LocalDateTime,
        val isAllExpenses: Boolean = false,
) : DropdownElement

@Composable
fun quickRanges(): List<QuickRangeDataV2> {
    return listOf(
            QuickRangeDataV2(
                    name = stringResource(R.string.today),
                    beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                    endDate = LocalDateTime.now().plusHours(1),
            ),
            QuickRangeDataV2(
                    name = stringResource(R.string.yesterday),
                    beginDate = LocalDateTime.now().minusDays(1).atStartOfTheDay(),
                    endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX),
            ),
            QuickRangeDataV2(
                    name = stringResource(R.string.dayBeforeYesterday),
                    beginDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN),
                    endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX),
            ),
            QuickRangeDataV2(
                    name = stringResource(R.string.lastWeek),
                    beginDate = LocalDateTime.now().minusDays(7),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    name = stringResource(R.string.thisMonth),
                    beginDate = LocalDateTime.now().atStartOfTheMonth(),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    name = stringResource(R.string.lastMonth),
                    beginDate = LocalDateTime.now().minusMonths(1),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    name = stringResource(R.string.lastThreeMonths),
                    beginDate = LocalDateTime.now().minusMonths(3),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    beginDate = minDate,
                    endDate = maxDate,
                    name = stringResource(R.string.allExpenses),
                    isAllExpenses = true,
            )
    )

}

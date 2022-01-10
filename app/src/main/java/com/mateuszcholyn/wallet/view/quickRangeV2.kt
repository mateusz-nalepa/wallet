package com.mateuszcholyn.wallet.view

import com.mateuszcholyn.wallet.util.atStartOfTheMonth
import com.mateuszcholyn.wallet.util.maxDate
import com.mateuszcholyn.wallet.util.minDate
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime


object QuickRangeV2 {

    private val quickRangesList = listOf(
            QuickRangeDataV2(
                    name = "Dzisiaj",
                    beginDate = LocalDateTime.of(LocalDate.now(), LocalTime.MIN),
                    endDate = LocalDateTime.now().plusHours(1),
            ),
            QuickRangeDataV2(
                    name = "Wczoraj",
                    beginDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MIN),
                    endDate = LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.MAX),
            ),
            QuickRangeDataV2(
                    name = "Przedwczoraj",
                    beginDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MIN),
                    endDate = LocalDateTime.of(LocalDate.now().minusDays(2), LocalTime.MAX),
            ),
            QuickRangeDataV2(
                    name = "Ostatni tydzień",
                    beginDate = LocalDateTime.now().minusDays(7),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    name = "Ten Miesiąc",
                    beginDate = LocalDateTime.now().atStartOfTheMonth(),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    name = "Ostatni Miesiąc",
                    beginDate = LocalDateTime.now().minusMonths(1),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    name = "Ostatnie 3 Miesiące",
                    beginDate = LocalDateTime.now().minusMonths(3),
                    endDate = LocalDateTime.now(),
            ),
            QuickRangeDataV2(
                    beginDate = minDate,
                    endDate = maxDate,
                    name = "Wszystkie wydatki",
            )
    )


    fun quickRanges(): List<QuickRangeDataV2> =
            quickRangesList

}

class QuickRangeDataV2(
        val name: String,
        val beginDate: LocalDateTime,
        val endDate: LocalDateTime,
)